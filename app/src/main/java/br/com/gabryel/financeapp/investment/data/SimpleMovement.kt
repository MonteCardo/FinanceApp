package br.com.gabryel.financeapp.investment.data

import org.joda.money.Money
import org.threeten.bp.LocalDate

/**
 * Simple implementation of a movement.
 *
 * Created by gabryel on 04/04/16.
 */
class SimpleMovement(override val identification: String = "",
                     override val movementDate: LocalDate = LocalDate.now(),
                     override val investedPeriod: Money) : Movement {

    private val ZERO = Money.zero(investedPeriod.currencyUnit)

    override val investedTotal: Money
        get() = investedPeriod

    override val available: Money
        get() = investedPeriod

    override val netGainPeriod: Money
        get() = ZERO

    override val netGainTotal: Money
        get() = ZERO
}