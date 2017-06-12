package br.com.gabryel.financeapp.investment.data

/**
 * Representation of an Wallet
 *
 * Created by gabryel on 04/04/16.
 */
interface Wallet : Moneyable {

    /**
     * Add a investment to this wallet
     *
     * @param investment Internal investment data
     */
    fun add(investment: Moneyable)
}
