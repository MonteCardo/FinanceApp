package br.com.montecardo.finanseer.calendar

import org.threeten.bp.LocalDate

/**
 * Class used to represent a range of dates
 *
 * Created by gabryel on 1/22/17.
 */
class DateRange(override val endInclusive: LocalDate,
                override val start: LocalDate) : ClosedRange<LocalDate>, Iterable<LocalDate> {

    override fun iterator(): Iterator<LocalDate> = DayIterator(this)

    class DayIterator(val range: DateRange) : Iterator<LocalDate> {
        var actual = range.start

        override fun next(): LocalDate {
            val res = actual
            actual = actual.plusDays(1)
            return res
        }

        override fun hasNext(): Boolean = actual <= range.endInclusive
    }
}