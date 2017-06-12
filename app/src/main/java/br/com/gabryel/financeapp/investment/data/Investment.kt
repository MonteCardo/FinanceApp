package br.com.gabryel.financeapp.investment.data

import org.threeten.bp.LocalDate

/**
 * Interface used to define what are the minimum requirements of an investment
 *
 * Created by gabryel on 04/04/16.
 */
interface Investment : Moneyable {

    /**
     * Add a movement to this investment
     *
     * @param movement Movement with the needed data
     */
    fun add(movement: Movement)

    /**
     * Get rows containing new information
     *
     * @param finalDate Final date to calculate
     * @return List of relevant states
     */
    fun getRows(finalDate: LocalDate): List<State>
}
