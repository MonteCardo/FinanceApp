package br.com.gabryel.financeapp.investment;

import java.util.Calendar;
import java.util.Date;

/**
 * Interface used to define what are the minimum requirements of a movement
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public interface Movement {

    Date getMovementDate();

    double getValue();
}