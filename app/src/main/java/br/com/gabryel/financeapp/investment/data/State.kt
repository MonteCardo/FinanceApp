package br.com.gabryel.financeapp.investment.data

import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * Interface defining a point of data for use in a table or graph
 *
 * Created by gabryel on 20/04/16.
 */
interface State {
    /**
     * Identification of the current state
     */
    val identification: String

    /**
     * Invested amount on a moment
     */
    val investedPeriod: Money

    /**
     * Net gain on a moment
     */
    val netGainPeriod: Money

    /**
     * Total invested until this moment
     */
    val investedTotal: Money

    /**
     * Total net gain until this moment
     */
    val netGainTotal: Money

    /**
     * Available quantity of money on a moment
     */
    val available: Money
}

interface DatedState : State {
    /**
     * Get the base date of a movement
     *
     * @return
     */
    val movementDate: org.threeten.bp.LocalDate
}

val NO_MONEY: Money = Money.zero(CurrencyUnit.USD)

class DummyState(override val identification: String,
                 override val investedPeriod: Money = NO_MONEY,
                 override val netGainPeriod: Money = NO_MONEY,
                 override val investedTotal: Money = NO_MONEY,
                 override val netGainTotal: Money = NO_MONEY) : State {

    override val available: Money = investedTotal + netGainTotal
}