package br.com.gabryel.financeapp.investment;


import org.threeten.bp.LocalDate;

/**
 * Interface used to define what are the minimum requirements of a movement
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public interface Movement {

	/**
	 * Get the base date of a movement
	 *
	 * @return
	 */
	LocalDate getMovementDate();

	/**
	 * Get the value of a movement
	 *
	 * @return
	 */
	double getValue();
}