package br.com.gabryel.financeapp.investment;

import java.util.Date;

/**
 * Interface used to define what are the minimum requirements of an investment
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public interface Investment extends Moneyable {

    /**
     * Add a movement to this investment
     *
     * @param movement Movement with the needed data
     */
    void add(Movement movement);
}
