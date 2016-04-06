package br.com.gabryel.financeapp.investment;

import java.util.Date;

/**
 * Simple implementation of a movement.
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public class SimpleMovement implements Movement {

    private final Date movementDate;

    private final double value;

    public SimpleMovement(Date movementDate, double value) {
        this.movementDate = movementDate;
        this.value = value;
    }

    @Override
    public Date getMovementDate() {
        return movementDate;
    }

    @Override
    public double getValue() {
        return value;
    }
}
