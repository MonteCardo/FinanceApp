package br.com.montecardo.finanseer.persistence

import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.Movement

/**
 * Temporary DAO interface. Made this way to delay to encapsulate the persistence and still be
 * flexible and easy for a beginner to experiment new ideas. In the future probably will be
 * substituted by a better mechanism.
 *
 * Created by gabryel on 20/09/17.
 */
interface FinanceDAO {

    /**
     * Persists the given investment, overwriting if it already exists an item with the same id.
     */
    fun persist(inv: Investment)

    /**
     * Persists the given movement, overwriting if it already exists an item with the same id.
     */
    fun persist(mov: Movement)

    /**
     * Get the investment by his id.
     */
    fun getInvestment(id: String): Investment?

    /**
     * Get the complete list of investments. Almost certainly will be changed when the system
     * changes to a multi-user persistence device.
     */
    fun getAllInvestments(): List<Investment>

    /**
     * Get the movements linked to a certain investment.
     */
    fun getMovementsByInvestment(invId: String): List<Movement>
}