package br.com.gabryel.financeapp.investment

import org.threeten.bp.LocalDate

import java.util.ArrayList
import java.util.Arrays
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.HashMap
import java.util.TreeMap

import br.com.gabryel.financeapp.calendar.DataPoint
import br.com.gabryel.financeapp.calendar.Periodicity
import br.com.gabryel.financeapp.calendar.Recurrence

/**
 * Simple representation of an monthly investment.
 *
 * Created by gabryel on 04/04/16.
 */
class MonthlyInvestment(override val name: String, private val monthlyInterest: Double) : Investment {

    companion object {
        private val LAST_MONTH_NUMBER = 11
    }

    private val movements = TreeMap<LocalDate, Movement>()

    private val yearRows = TreeMap<Int, YearPoint>()

    private val recurrent = HashMap<Recurrence, Movement>()

    override fun add(movement: Movement) {
        var movement = movement
        val oldMovement = movements[movement.movementDate]
        if (oldMovement != null) {
            movement = SimpleMovement(movement.movementDate, movement, oldMovement)
        }

        movements.put(movement.movementDate, movement)
    }

    override fun getRows(type: Periodicity, from: LocalDate, to: LocalDate): List<DataPoint> {
        while (!yearRows.containsKey(from.year)) {
            createNextYear()
        }

        while (!yearRows.containsKey(to.year)) {
            createNextYear()
        }

        val rows = ArrayList<DataPoint>()
        for (i in from.year..to.year) {
            var row = yearRows[i]
            if (row != null) {
                when (type) {
                    Periodicity.YEAR -> rows.add(row)
                    Periodicity.MONTH -> rows.addAll(row.monthRows)
                    Periodicity.DAY -> rows.addAll(row.dayRows)
                }
            }
        }

        return rows
    }

    override fun getRows(type: Periodicity, to: LocalDate): List<DataPoint> {
        while (!yearRows.containsKey(to.year)) {
            createNextYear()
        }

        val rows = ArrayList<DataPoint>()
        for (i in yearRows.firstKey()..to.year) {
            var row = yearRows[i]
            if (row != null) {
                when (type) {
                    Periodicity.YEAR -> rows.add(row)
                    Periodicity.MONTH -> rows.addAll(row.monthRows)
                    Periodicity.DAY -> rows.addAll(row.dayRows)
                }
            }
        }

        return rows
    }

    /**
     * Create a new year and put it on the yearly map
     */
    private fun createNextYear() {
        if (yearRows.isEmpty()) {
            val firstInvestmentYear = movements.firstKey().year
            yearRows.put(firstInvestmentYear, YearPoint(firstInvestmentYear))
        } else {
            val year = yearRows.lastEntry().value.rollNextYear()
            yearRows.put(yearRows.lastKey(), year)
        }
    }

    private inner class YearPoint : DataPoint {
        private val months: Array<MonthPoint>

        override var investedPeriod = 0.0

        override var netGainPeriod = 0.0

        private val year: Int

        internal constructor(year: Int) {
            months = Array(12, {i -> MonthPoint(year, i + 1)})
            this.year = year

            months[0] = MonthPoint(year, 1)
            investedPeriod = months[0].investedPeriod
            netGainPeriod = months[0].netGainPeriod

            for (i in 1..11) {
                months[i] = months[i - 1].rollNextMonth()
                investedPeriod += months[i].investedPeriod
                netGainPeriod += months[i].netGainPeriod
            }
        }

        private constructor(year: Int, lastMonth: MonthPoint) {
            months = Array(12, {i -> MonthPoint(year, i + 1)})
            var lastMonth = lastMonth
            this.year = year

            for (i in 0..11) {
                lastMonth = lastMonth.rollNextMonth()
                months[i] = lastMonth
                netGainPeriod += months[i].netGainPeriod
                investedPeriod += months[i].investedPeriod
            }
        }

        /**
         * Create next year using this one as a basis
         *
         * @return
         */
        internal fun rollNextYear(): YearPoint {
            return YearPoint(year + 1, lastMonth)
        }

        override val identification: String
            get() = year.toString()

        override var investedTotal = 0.0
            get() = lastMonth.investedTotal

        override var netGainTotal = 0.0
            get() = lastMonth.netGainTotal

        override var available = 0.0
            get() = lastMonth.available

        override fun hasMoreData(): Boolean {
            return true
        }

        private val lastMonth: MonthPoint
            get() = months[LAST_MONTH_NUMBER]

        /**
         * TODO For testing of proof of concept, probably will be deprecated/deleted
         *
         * Get daily rows from year.
         *
         * @return
         */
        internal val dayRows: List<DataPoint>
            get() {
                val rows = ArrayList<DataPoint>()

                for (i in months.indices) {
                    rows.addAll(months[i].dayRows)
                }

                return rows
            }

        /**
         * TODO For testing of proof of concept, probably will be deprecated/deleted
         *
         * Get monthly rows from year.
         *
         * @return
         */
        internal val monthRows: List<DataPoint>
            get() = ArrayList<DataPoint>(Arrays.asList<MonthPoint>(*months))
    }

    private inner class MonthPoint : DataPoint {

        private val year: Int

        private val month: Int

        private val lastDay: Int

        override var investedPeriod = 0.0

        override var netGainPeriod = 0.0

        private var unusedDays = ArrayList<DayPoint>()

        private val days = TreeMap<Int, DayPoint>()

        internal constructor(year: Int, month: Int) {
            this.year = year
            this.month = month

            val cal = startOfMonth
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            var yesterday: DayPoint? = null
            for (day in 1..lastDay) {
                val date = LocalDate.of(year, month, day)
                yesterday = DayPoint(date, yesterday)
                days.put(day, yesterday)

                investedPeriod += yesterday.investedPeriod
                netGainPeriod += yesterday.netGainPeriod
            }
        }

        private constructor(year: Int, month: Int, lastMonth: MonthPoint) {
            this.year = year
            this.month = month
            this.unusedDays = ArrayList(lastMonth.days.values)

            val cal = startOfMonth
            var date = LocalDate.of(year, month, 1)

            var yesterday = unusedDays[unusedDays.size - 1]
            yesterday = DayPoint(date, yesterday, unusedDays.removeAt(0), lastMonth.unusedDays)
            days.put(1, yesterday)

            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (day in 2..lastDay) {
                investedPeriod += yesterday.investedPeriod
                netGainPeriod += yesterday.netGainPeriod

                date = LocalDate.of(year, month, day)

                if (unusedDays.size > 0) {
                    yesterday = DayPoint(date, yesterday, unusedDays.removeAt(0))
                } else {
                    yesterday = DayPoint(date, yesterday)
                }

                days.put(day, yesterday)
            }

            investedPeriod += yesterday.investedPeriod
            netGainPeriod += yesterday.netGainPeriod
        }

        /**
         * Creates next month using the actual as a base

         * @return
         */
        internal fun rollNextMonth(): MonthPoint {
            val nextMonth = month % 12 + 1
            val newYear = if (nextMonth == 1) year + 1 else year

            return MonthPoint(newYear, nextMonth, this)
        }


        /**
         * TODO For testing of proof of concept, probably will be deprecated/deleted
         *
         * Get daily rows from month.
         *
         * @return
         */
        internal val dayRows: List<DataPoint>
            get() = ArrayList<DataPoint>(days.values)

        /**
         * Creates a calendar with the start of the month as set time.
         *
         * @return
         */
        private val startOfMonth: Calendar
            get() {
                val cal = GregorianCalendar()
                cal.clear()
                cal.set(year, month, 1)

                return cal
            }

        override val identification: String
            get() = String.format("%d/%d", month, year)

        override var investedTotal = 0.0
            get() = days[lastDay]?.investedTotal ?: 0.0

        override var netGainTotal = 0.0
            get() = days[lastDay]?.netGainTotal ?: 0.0

        override var available = 0.0
            get() = days[lastDay]?.available ?: 0.0

        override fun hasMoreData(): Boolean {
            return true
        }
    }

    private inner class DayPoint internal constructor(private val date: LocalDate) : DataPoint {

        private var onDay = 0.0

        override var netGainTotal = 0.0

        override var investedTotal = 0.0

        override var netGainPeriod = 0.0

        override var investedPeriod = 0.0

        init {
            val today = LocalDate.now()
            if (today.isAfter(date)) {
                computeMovement(this@MonthlyInvestment.movements[date])
            } else {
                for ((key, value) in recurrent) {
                    if (key.matchs(today)) {
                        computeMovement(value)
                    }
                }
            }
        }

        internal constructor(date: LocalDate, yesterday: DayPoint?) : this(date) {
            if (yesterday != null) {
                investedTotal += yesterday.investedTotal
                netGainTotal = yesterday.netGainTotal
            }
        }

        internal constructor(date: LocalDate, yesterday: DayPoint, lastMonthDay: DayPoint) : this(date, yesterday) {
            netGainPeriod = lastMonthDay.onDay * monthlyInterest
            onDay += lastMonthDay.onDay + netGainPeriod
            netGainTotal += netGainPeriod
        }

        internal constructor(date: LocalDate, yesterday: DayPoint, lastMonthDay: DayPoint,
                             lastMonthRelatives: List<DayPoint>) : this(date, yesterday, lastMonthDay) {
            var relativeNetGain = 0.0
            for (row in lastMonthRelatives) {
                relativeNetGain += row.onDay * monthlyInterest
            }

            onDay += relativeNetGain
            netGainPeriod += relativeNetGain
            netGainTotal += relativeNetGain
        }

        /**
         * Computes the current movement and set the Data Point parameters accordingly

         * @param movement Movement to be computed
         */
        private fun computeMovement(movement: Movement?) {
            if (movement != null) {
                investedPeriod = movement.value
                investedTotal = investedPeriod
                onDay = investedPeriod
            }
        }

        override val identification: String
            get() = String.format("%d/%d/%d", date.dayOfMonth, date.monthValue, date.year)

        override var available = 0.0
            get() = netGainTotal + investedTotal

        override fun hasMoreData(): Boolean {
            return false
        }
    }
}
