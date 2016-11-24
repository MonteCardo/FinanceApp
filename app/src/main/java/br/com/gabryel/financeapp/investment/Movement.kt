package br.com.gabryel.financeapp.investment


import org.threeten.bp.LocalDate

/**
 * Interface used to define what are the minimum requirements of a movement
 *
 * Created by gabryel on 04/04/16.
 */
interface Movement {

    /**
     * Get the base date of a movement
     *
     * @return
     */
    val movementDate: LocalDate

    /**
     * Get the value of a movement
     *
     * @return
     */
    val value: Double
}