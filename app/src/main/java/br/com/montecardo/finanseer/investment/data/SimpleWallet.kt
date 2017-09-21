package br.com.montecardo.finanseer.investment.data

import java.util.*

/**
 * Simple implementation of Wallet class.
 *
 *
 * Created by gabryel on 05/04/16.
 */
class SimpleWallet(
        override val id: String = UUID.randomUUID().toString(),
        override val name: String) : Wallet