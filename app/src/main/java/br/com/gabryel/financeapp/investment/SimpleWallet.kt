package br.com.gabryel.financeapp.investment

/**
 * Simple implementation of Wallet class.
 *
 *
 * Created by gabryel on 05/04/16.
 */
class SimpleWallet(override val name: String) : Wallet {

    private val investments = mutableListOf<Moneyable>()

    override fun add(investment: Moneyable) {
        investments.add(investment)
    }
}