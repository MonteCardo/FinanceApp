package br.com.gabryel.financeapp.investment

import br.com.gabryel.financeapp.calendar.DateRange
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

    override fun getRows(finalDate: LocalDate): List<State> {
        if (movements.size == 0){
            return emptyList()
        }

        val allStates = mutableListOf<State>()
        val statesByDay = mutableMapOf<Int, State>()
        val range = DateRange(finalDate, movements.map { it.movementDate }.min() ?: finalDate)

        for (date in range) {
            // FIXME Para repoduzir o modelo do Itau e facilitar protótipo, pensar em algo melhor no futuro
            if (date.dayOfMonth > 28) {
                continue
            }

            val movementsOfTheDay = movements.filter { it.movementDate == date }
            // val previousDay = statesByDay.getOrElse(date.dayOfMonth, )
        }

        // TODO Devolver de verdade q lista de estados
        return movements
    }
}