package br.com.gabryel.financeapp.investment

import org.joda.money.Money
import org.threeten.bp.LocalDate

/**
 * Interface defining a point of data for use in a table or graph
 *
 * Created by gabryel on 20/04/16.
 */
interface State {
    /**
     * Get the invested amount on a moment
     *
     * @return
     */
    val investedPeriod: Money

    /**
     * Get the net gain on a moment
     *
     * @return
     */
    val netGainPeriod: Money

    /**
     * Get the total invested until this moment
     *
     * @return
     */
    val investedTotal: Money

    /**
     * Get the total net gain until this moment
     *
     * @return
     */
    val netGainTotal: Money

    /**
     * Get the available quantity of money on a moment
     *
     * @return
     */
    val available: Money
}

interface DatedState : State {
    /**
     * Get the base date of a movement
     *
     * @return
     */
    val movementDate: LocalDate
}