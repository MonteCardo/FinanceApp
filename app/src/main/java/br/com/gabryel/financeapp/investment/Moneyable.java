package br.com.gabryel.financeapp.investment;

/**
 * Means that you can get money data from the classes inheriting this interface
 * <p/>
 * Created by gabryel on 05/04/16.
 */

// TODO GIVE THIS INTERFACE A BETTER NAME, GODDAMNIT
public interface Moneyable {

    /**
     * Get name of the investment
     *
     * @return Name of the investment
     */
    String getName();
}
