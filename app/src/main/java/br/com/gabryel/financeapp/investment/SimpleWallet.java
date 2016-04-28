package br.com.gabryel.financeapp.investment;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of Wallet class.
 * <p/>
 * Created by gabryel on 05/04/16.
 */
public class SimpleWallet implements Wallet {

	private String name;

	private List<Moneyable> investments = new ArrayList<>();

	public SimpleWallet(String name) {
		this.name = name;
	}

	@Override
	public void add(Moneyable investment) {
		investments.add(investment);
	}

	@Override
	public String getName() {
		return name;
	}
}