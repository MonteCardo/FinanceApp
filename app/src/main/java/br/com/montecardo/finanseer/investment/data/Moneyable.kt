package br.com.montecardo.finanseer.investment.data

/**
 * Means that you can get money data from the classes inheriting this interface
 *
 * Created by gabryel on 05/04/16.
 */
interface Moneyable {

    val id: String

    /**
     * Get name of the investmentId
     *
     * @return Name of the investmentId
     */
    val name: String
}
