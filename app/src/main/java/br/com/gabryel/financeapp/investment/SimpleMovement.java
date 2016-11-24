package br.com.gabryel.financeapp.investment;

import org.threeten.bp.LocalDate;

/**
 * Simple implementation of a movement.
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public class SimpleMovement implements Movement {

	private final LocalDate movementDate;

	private final double value;

	public SimpleMovement(LocalDate movementDate, Movement... movements) {
		this.movementDate = movementDate;
		double partialValue = 0;

		for (Movement mov : movements) {
			partialValue += mov.getValue();
		}

		this.value = partialValue;
	}

	public SimpleMovement(LocalDate movementDate, double value) {
		this.movementDate = movementDate;
		this.value = value;
	}

	@Override
	public LocalDate getMovementDate() {
		return movementDate;
	}

	@Override
	public double getValue() {
		return value;
	}
}
