package br.com.gabryel.financeapp.calendar;


import org.threeten.bp.LocalDate;

/**
 * Matcher for a recurrence, says if the date conform with the rules.
 * <p/>
 * Created by gabryel on 22/04/16.
 */
public interface Recurrence {

	/**
	 * Allows checking if the given date matches with the parameters of the recurrence
	 *
	 * @param date
	 * @return
	 */
	boolean matchs(LocalDate date);
}
