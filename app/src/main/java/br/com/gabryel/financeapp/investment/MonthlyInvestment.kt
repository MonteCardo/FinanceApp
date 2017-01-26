package br.com.gabryel.financeapp.investment

import br.com.gabryel.financeapp.calendar.DateRange
import org.joda.money.Money
import org.threeten.bp.LocalDate

/**
 * Simple representation of an monthly investment.
 *
 * Created by gabryel on 04/04/16.
 */
class MonthlyInvestment(override val name: String, private val monthlyInterest: Double) : Investment {
    private val movements = mutableListOf<Movement>()

    override fun add(movement: Movement) {
        movements.add(movement)
    }

    override fun getRows(lastDate: LocalDate): List<State> {
        if (movements.size == 0){
            return emptyList()
        }

        val statesByDay = mapOf<Int, State>()
        val range = DateRange(lastDate, movements.map { it.movementDate }.min()!! )

        for (date in range) {
            val movementsOfTheDay = movements.filter { it.movementDate == date }
        }

        // TODO Devolver de verdade q lista de estados
        return movements
    }
}