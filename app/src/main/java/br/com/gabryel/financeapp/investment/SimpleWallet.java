package br.com.gabryel.financeapp.investment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public double getAvailableMoneyOn(Date date) {
        double value = 0;

        for (Moneyable investment : investments) {
            value += investment.getAvailableMoneyOn(date);
        }

        return value;
    }

    @Override
    public double getInvestedMoneyOn(Date date) {
        double value = 0;

        for (Moneyable investment : investments) {
            value += investment.getInvestedMoneyOn(date);
        }

        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Double> getWalletInfoOnDate(Date date) {
        Map<String, Double> walletMap = new HashMap<>();

        for (Moneyable inv : investments) {
            walletMap.put(inv.getName(), inv.getAvailableMoneyOn(date));
        }

        return walletMap;
    }
}