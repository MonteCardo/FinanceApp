package br.com.gabryel.financeapp.investment;

import java.util.Date;
import java.util.Map;

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

    /**
     * Get internal investment data on wallet
     *
     * @param date Date for the search
     * @return Map containing names and values of investment on date
     */
    Map<String, Double> getWalletInfoOnDate(Date date);
}
