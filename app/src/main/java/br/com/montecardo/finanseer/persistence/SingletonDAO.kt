package br.com.montecardo.finanseer.persistence

import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.Movement

/**
 * Singleton DAO used so app can maintain coherent state for all classes. I know, it isn't one of
 * my best ideas, but I am making it for experimentation.
 *
 * Created by gabryel on 24/06/17.
 */
object SingletonDAO : FinanceDAO {

    /*
     * Oh, and I would like to use an delegate here, but it seems kotlin doesn't support delegates
     * for singleton objects D:
     */
    private var dao: FinanceDAO = VolatileMemoryDao()

    fun useProvider(dao: FinanceDAO) {
        this.dao = dao
    }

    override fun persist(inv: Investment) = dao.persist(inv)

    override fun persist(mov: Movement) = dao.persist(mov)

    override fun getInvestment(id: String) = dao.getInvestment(id)

    override fun getAllInvestments() = dao.getAllInvestments()

    override fun getMovementsByInvestment(invId: String) = dao.getMovementsByInvestment(invId)
}