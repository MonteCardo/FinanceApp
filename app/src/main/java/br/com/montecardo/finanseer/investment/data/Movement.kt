package br.com.montecardo.finanseer.investment.data

import org.joda.money.Money
import org.threeten.bp.LocalDate
import java.util.*

/**
 * Interface used to define what are the minimum requirements of a movement
 *
 * Created by gabryel on 04/04/16.
 */
data class Movement(val investmentId: String,
                    override val name: String,
                    override val id: String = UUID.randomUUID().toString(),
                    val date: LocalDate,
                    val money: Money) : Moneyable