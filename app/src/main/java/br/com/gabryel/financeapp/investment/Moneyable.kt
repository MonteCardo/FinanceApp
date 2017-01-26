package br.com.gabryel.financeapp.investment

/**
 * Means that you can get money data from the classes inheriting this interface
 *
 * Created by gabryel on 05/04/16.
 */

// TODO GIVE THIS INTERFACE A BETTER NAME, GODDAMNIT
interface Moneyable {
    /**
     * Get name of the investment
     *
     * @return Name of the investment
     */
    val name: String
}
