package br.com.montecardo.finanseer.investment.data

import java.util.*

/**
 * Created by gabryel on 15/06/17.
 */
class SimpleInvestment(
        override val id: String = UUID.randomUUID().toString(),
        override val name: String) : Investment
