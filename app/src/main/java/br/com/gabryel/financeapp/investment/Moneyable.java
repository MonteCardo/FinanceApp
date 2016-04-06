package br.com.gabryel.financeapp.investment;

import java.util.Date;

/**
 * Means that you can get money data from the classes inheriting this interface
 * <p/>
 * Created by gabryel on 05/04/16.
 */

// TODO GIVE THIS INTERFACE A BETTER NAME, GODDAMNIT
public interface Moneyable {
    /**
     * Get value available for withdrawal on a given date
     *
     * @param date Date
     * @return Value available for withdrawal
     */
    double getAvailableMoneyOn(Date date);

    /**
     * Get value invested until date
     *
     * @param date Date
     * @return Value that was invested until given date
     */
    double getInvestedMoneyOn(Date date);

    /**
     * Get name of the investment
     *
     * @return Name of the investment
     */
    String getName();
}
