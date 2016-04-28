package br.com.gabryel.financeapp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.gabryel.financeapp.calendar.DataPoint;
import br.com.gabryel.financeapp.calendar.Periodicity;
import br.com.gabryel.financeapp.date.LocalDate;
import br.com.gabryel.financeapp.date.SimpleLocalDate;
import br.com.gabryel.financeapp.investment.Investment;
import br.com.gabryel.financeapp.investment.Moneyable;
import br.com.gabryel.financeapp.investment.MonthlyInvestment;
import br.com.gabryel.financeapp.investment.SimpleMovement;
import br.com.gabryel.financeapp.investment.SimpleWallet;
import br.com.gabryel.financeapp.investment.Wallet;

/**
 * Proof of Concept of the base of the app
 * <p/>
 * Created by gabryel on 05/04/16.
 */
public class ProofOfConcepter {

	public static void main(String[] args) {
		Wallet wallet = createWallet();
	}

	private static void printInvestment(Investment investment, LocalDate date) {
		List<DataPoint> rows = investment.getRowsUntil(Periodicity.DAY, date);

		System.out.println(investment.getName());
		for (DataPoint row : rows) {
			System.out.println(
					String.format("%s: %.2f - %.2f - %.2f - %.2f - %.2f", row.getIdentification(),
							row.getAvailable(), row.getInvestedPeriod(), row.getInvestedTotal(),
							row.getNetGainPeriod(), row.getNetGainTotal()));
		}

		System.out.println();
	}

	private static Wallet createWallet() {
		Wallet wallet = new SimpleWallet("Total");

		wallet.add(getAllowances());
		wallet.add(createBankInvestment());

		printInvestment(createMyInvestment(), createDate(21, 4, 2016));

		return wallet;
	}

	private static Investment createBankInvestment() {
		Investment investment = new MonthlyInvestment("Conta Corrente", 0);

		investment.add(new SimpleMovement(createDate(1, 1, 2016), 1733.41));
		investment.add(new SimpleMovement(createDate(15, 1, 2016), 1105.05));

		investment.add(new SimpleMovement(createDate(1, 2, 2016), 1733.41));
		investment.add(new SimpleMovement(createDate(15, 2, 2016), 1105.05));

		investment.add(new SimpleMovement(createDate(1, 3, 2016), 1733.41));
		investment.add(new SimpleMovement(createDate(15, 3, 2016), 1105.05));

		investment.add(new SimpleMovement(createDate(1, 4, 2016), 1733.41));
		investment.add(new SimpleMovement(createDate(15, 4, 2016), 1105.05));

		investment.add(new SimpleMovement(createDate(17, 1, 2016), -2800));
		investment.add(new SimpleMovement(createDate(17, 2, 2016), -2800));
		investment.add(new SimpleMovement(createDate(17, 3, 2016), -2800));
		investment.add(new SimpleMovement(createDate(17, 4, 2016), -2800));

		return investment;
	}

	private static Moneyable getAllowances() {
		Wallet allowance = new SimpleWallet("Poupança");

		allowance.add(createFatherInvestment());
		allowance.add(createMyInvestment());

		return allowance;
	}

	private static Investment createMyInvestment() {
		Investment investment = new MonthlyInvestment("Gabryel", 0.00599);

		investment.add(new SimpleMovement(createDate(25, 1, 2016), 2089.04));
		investment.add(new SimpleMovement(createDate(5, 2, 2016), 1100));
		investment.add(new SimpleMovement(createDate(9, 2, 2016), 95.02));
		investment.add(new SimpleMovement(createDate(11, 2, 2016), 250));
		investment.add(new SimpleMovement(createDate(14, 2, 2016), 200));
		investment.add(new SimpleMovement(createDate(15, 2, 2016), 100));
		investment.add(new SimpleMovement(createDate(18, 2, 2016), 100));
		investment.add(new SimpleMovement(createDate(21, 2, 2016), 850));
		investment.add(new SimpleMovement(createDate(28, 2, 2016), 100));

		investment.add(new SimpleMovement(createDate(1, 3, 2016), 600));
		investment.add(new SimpleMovement(createDate(5, 3, 2016), 100));
		investment.add(new SimpleMovement(createDate(9, 3, 2016), 500));
		investment.add(new SimpleMovement(createDate(11, 3, 2016), 161.61));
		investment.add(new SimpleMovement(createDate(14, 3, 2016), 650));
		investment.add(new SimpleMovement(createDate(15, 3, 2016), 170));

		investment.add(new SimpleMovement(createDate(1, 4, 2016), -4300));
		investment.add(new SimpleMovement(createDate(1, 4, 2016), 950));
		investment.add(new SimpleMovement(createDate(1, 4, 2016), 30));
		investment.add(new SimpleMovement(createDate(1, 4, 2016), 142.94));
		investment.add(new SimpleMovement(createDate(1, 4, 2016), 50));

		return investment;
	}

	private static Investment createFatherInvestment() {
		Investment investment = new MonthlyInvestment("Pai", 0.00599);

		investment.add(new SimpleMovement(createDate(1, 4, 2016), 4300));
		investment.add(new SimpleMovement(createDate(2, 4, 2016), -400));

		return investment;
	}

	private static LocalDate createDate(int day, int month, int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day);

		return new SimpleLocalDate(cal.getTime());
	}
}
