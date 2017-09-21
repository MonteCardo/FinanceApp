package br.com.montecardo.finanseer.persistence

import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.Movement

/**
 * DAO implementation in memory.
 *
 * Created by gabryel on 20/09/17.
 */
class VolatileMemoryDao : FinanceDAO {

    private val investments: MutableMap<String, Investment> = mutableMapOf()

    private val movements: MutableMap<String, Movement> = mutableMapOf()

    override fun persist(inv: Investment) {
        investments[inv.id] = inv
    }

    override fun persist(mov: Movement) {
        movements[mov.id] = mov
    }

    override fun getInvestment(id: String) = investments[id]

    override fun getAllInvestments() = investments.values.toList()

    override fun getMovementsByInvestment(invId: String) =
            movements.values.filter { it.investmentId == invId }
}