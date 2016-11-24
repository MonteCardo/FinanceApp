package br.com.gabryel.financeapp.investment

import java.util.ArrayList

/**
 * Simple implementation of Wallet class.
 *
 *
 * Created by gabryel on 05/04/16.
 */
class SimpleWallet(override val name: String) : Wallet {

    private val investments = ArrayList<Moneyable>()

    override fun add(investment: Moneyable) {
        investments.add(investment)
    }
}