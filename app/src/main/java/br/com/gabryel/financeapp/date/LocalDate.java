package br.com.gabryel.financeapp.date;

/**
 * Used to represent the union of a day, month and year. Change when using Java 8 to Oracle's
 * implementation of LocalDate
 * <p/>
 * Created by gabryel on 21/04/16.
 */
public interface LocalDate extends Comparable<LocalDate> {

	int getDayOfMonth();

	int getMonthValue();

	int getYear();

	boolean isAfter(LocalDate other);

	boolean isBefore(LocalDate other);

	boolean isEqual(LocalDate other);
}
