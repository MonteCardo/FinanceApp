package br.com.montecardo.finanseer.investment.data

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