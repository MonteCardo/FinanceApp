package br.com.gabryel.financeapp.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Simple implementarion of LocalDate
 * <p/>
 * Created by gabryel on 21/04/16.
 */
public class SimpleLocalDate implements LocalDate {

	private final int day;

	private final int month;

	private final int year;

	public SimpleLocalDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.month = calendar.get(Calendar.MONTH);
		this.year = calendar.get(Calendar.YEAR);
	}

	public SimpleLocalDate(Calendar calendar) {
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.month = calendar.get(Calendar.MONTH);
		this.year = calendar.get(Calendar.YEAR);
	}

	@Override
	public int getDayOfMonth() {
		return day;
	}

	@Override
	public int getMonthValue() {
		return month;
	}

	@Override
	public int getYear() {
		return year;
	}

	@Override
	public boolean isAfter(LocalDate other) {
		return compareTo(other) > 0;
	}

	@Override
	public boolean isBefore(LocalDate other) {
		return compareTo(other) < 0;
	}

	@Override
	public boolean isEqual(LocalDate other) {
		return compareTo(other) == 0;
	}

	@Override
	public int compareTo(LocalDate another) {
		if (another.getYear() != getYear()) {
			return another.getYear() > getYear() ? -1 : 1;
		}

		if (another.getMonthValue() != getMonthValue()) {
			return another.getMonthValue() > getMonthValue() ? -1 : 1;
		}

		if (another.getDayOfMonth() == getDayOfMonth()) {
			return 0;
		}

		return another.getDayOfMonth() > getDayOfMonth() ? -1 : 1;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;

		return o instanceof LocalDate && isEqual((LocalDate) o);
	}
}
