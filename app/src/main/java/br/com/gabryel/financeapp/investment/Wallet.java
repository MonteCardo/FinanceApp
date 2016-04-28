package br.com.gabryel.financeapp.investment;

/**
 * Representation of an Wallet
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public interface Wallet extends Moneyable {

	/**
	 * Add a investment to this wallet
	 *
	 * @param investment Internal investment data
	 */
	void add(Moneyable investment);
}
