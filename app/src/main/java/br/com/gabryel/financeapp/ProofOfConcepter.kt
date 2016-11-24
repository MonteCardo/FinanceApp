package br.com.gabryel.financeapp

import org.threeten.bp.LocalDate

import br.com.gabryel.financeapp.calendar.Periodicity
import br.com.gabryel.financeapp.investment.Investment
import br.com.gabryel.financeapp.investment.Moneyable
import br.com.gabryel.financeapp.investment.MonthlyInvestment
import br.com.gabryel.financeapp.investment.SimpleMovement
import br.com.gabryel.financeapp.investment.SimpleWallet
import br.com.gabryel.financeapp.investment.Wallet
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Proof of Concept of the base of the app
 *
 * Created by gabryel on 05/04/16.
 */
object ProofOfConcepter {

    @JvmStatic fun main(args: Array<String>) {
        createWallet()
    }

    private fun printInvestment(investment: Investment, date: LocalDate) {
        val rows = investment.getRows(Periodicity.DAY, date)

        println(investment.name)
        for (row in rows) {
            println(String.format("%s: %.2f - %.2f - %.2f - %.2f - %.2f", row.identification,
                    row.available, row.investedPeriod, row.investedTotal,
                    row.netGainPeriod, row.netGainTotal))
        }

        println()
    }

    private fun createWallet(): Wallet {
        val wallet = SimpleWallet("Total")

        wallet.add(allowances)
        wallet.add(createBankInvestment())

        printInvestment(createMyInvestment(), createDate(21, 4, 2016))

        return wallet
    }

    private fun createBankInvestment(): Investment {
        val investment = MonthlyInvestment("Conta Corrente", 0.0)

        investment.add(SimpleMovement(createDate(1, 1, 2016), 1733.41))
        investment.add(SimpleMovement(createDate(15, 1, 2016), 1105.05))

        investment.add(SimpleMovement(createDate(1, 2, 2016), 1733.41))
        investment.add(SimpleMovement(createDate(15, 2, 2016), 1105.05))

        investment.add(SimpleMovement(createDate(1, 3, 2016), 1733.41))
        investment.add(SimpleMovement(createDate(15, 3, 2016), 1105.05))

        investment.add(SimpleMovement(createDate(1, 4, 2016), 1733.41))
        investment.add(SimpleMovement(createDate(15, 4, 2016), 1105.05))

        investment.add(SimpleMovement(createDate(17, 1, 2016), -2800.0))
        investment.add(SimpleMovement(createDate(17, 2, 2016), -2800.0))
        investment.add(SimpleMovement(createDate(17, 3, 2016), -2800.0))
        investment.add(SimpleMovement(createDate(17, 4, 2016), -2800.0))

        return investment
    }

    private val allowances: Moneyable
        get() {
            val allowance = SimpleWallet("Poupança")

            allowance.add(createFatherInvestment())
            allowance.add(createMyInvestment())

            return allowance
        }

    private fun createMyInvestment(): Investment {
        val investment = MonthlyInvestment("Gabryel", 0.00599)

        investment.add(SimpleMovement(createDate(25, 1, 2016), 2089.04))
        investment.add(SimpleMovement(createDate(5, 2, 2016), 1100.0))
        investment.add(SimpleMovement(createDate(9, 2, 2016), 95.02))
        investment.add(SimpleMovement(createDate(11, 2, 2016), 250.0))
        investment.add(SimpleMovement(createDate(14, 2, 2016), 200.0))
        investment.add(SimpleMovement(createDate(15, 2, 2016), 100.0))
        investment.add(SimpleMovement(createDate(18, 2, 2016), 100.0))
        investment.add(SimpleMovement(createDate(21, 2, 2016), 850.0))
        investment.add(SimpleMovement(createDate(28, 2, 2016), 100.0))

        investment.add(SimpleMovement(createDate(1, 3, 2016), 600.0))
        investment.add(SimpleMovement(createDate(5, 3, 2016), 100.0))
        investment.add(SimpleMovement(createDate(9, 3, 2016), 500.0))
        investment.add(SimpleMovement(createDate(11, 3, 2016), 161.61))
        investment.add(SimpleMovement(createDate(14, 3, 2016), 650.0))
        investment.add(SimpleMovement(createDate(15, 3, 2016), 170.0))

        investment.add(SimpleMovement(createDate(1, 4, 2016), -4300.0))
        investment.add(SimpleMovement(createDate(1, 4, 2016), 950.0))
        investment.add(SimpleMovement(createDate(1, 4, 2016), 30.0))
        investment.add(SimpleMovement(createDate(1, 4, 2016), 142.94))
        investment.add(SimpleMovement(createDate(1, 4, 2016), 50.0))

        return investment
    }

    private fun createFatherInvestment(): Investment {
        val investment = MonthlyInvestment("Pai", 0.00599)

        investment.add(SimpleMovement(createDate(1, 4, 2016), 4300.0))
        investment.add(SimpleMovement(createDate(2, 4, 2016), -400.0))

        return investment
    }

    private fun createDate(day: Int, month: Int, year: Int): LocalDate {
        return LocalDate.of(year, month, day)
    }
}
