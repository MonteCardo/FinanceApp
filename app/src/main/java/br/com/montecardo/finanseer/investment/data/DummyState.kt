package br.com.montecardo.finanseer.investment.data

import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * Created by gabryel on 15/06/17.
 */

val NO_MONEY: Money = Money.zero(CurrencyUnit.USD)

class DummyState(override val identification: String,
                 override val investedPeriod: Money = NO_MONEY,
                 override val netGainPeriod: Money = NO_MONEY,
                 override val investedTotal: Money = NO_MONEY,
                 override val netGainTotal: Money = NO_MONEY) : State {

    override val available: Money = investedTotal + netGainTotal
}