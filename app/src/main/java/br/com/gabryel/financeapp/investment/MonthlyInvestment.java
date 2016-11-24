package br.com.gabryel.financeapp.investment;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.gabryel.financeapp.calendar.DataPoint;
import br.com.gabryel.financeapp.calendar.Periodicity;
import br.com.gabryel.financeapp.calendar.Recurrence;

/**
 * Simple representation of an monthly investment.
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public class MonthlyInvestment implements Investment {

	private final TreeMap<LocalDate, Movement> movements = new TreeMap<>();

	private final TreeMap<Integer, YearPoint> yearRows = new TreeMap<>();

	private final Map<Recurrence, Movement> recurrent = new HashMap<>();

	private final double monthlyInterest;

	private final String name;

	public MonthlyInvestment(String name, double monthlyInterest) {
		this.name = name;
		this.monthlyInterest = monthlyInterest;
	}

	@Override
	public void add(Movement movement) {
		Movement oldMovement = movements.get(movement.getMovementDate());
		if (oldMovement != null) {
			movement = new SimpleMovement(movement.getMovementDate(), movement, oldMovement);
		}

		movements.put(movement.getMovementDate(), movement);
	}

	@Override
	public List<DataPoint> getRows(Periodicity type, LocalDate from, LocalDate to) {
		while (!yearRows.containsKey(from.getYear())) {
			createNextYear();
		}

		while (!yearRows.containsKey(to.getYear())) {
			createNextYear();
		}

		List<DataPoint> rows = new ArrayList<>();
		for (int i = from.getYear(); i <= to.getYear(); i++) {
			switch (type) {
				case YEAR:
					rows.add(yearRows.get(i));
					break;
				case MONTH:
					rows.addAll(yearRows.get(i).getMonthRows());
					break;
				case DAY:
					rows.addAll(yearRows.get(i).getDayRows());
					break;
			}
		}

		return rows;
	}

	@Override
	public List<DataPoint> getRows(Periodicity type, LocalDate to) {
		while (!yearRows.containsKey(to.getYear())) {
			createNextYear();
		}

		List<DataPoint> rows = new ArrayList<>();
		for (int i =yearRows.firstKey(); i <= to.getYear(); i++) {
			switch (type) {
				case YEAR:
					rows.add(yearRows.get(i));
					break;
				case MONTH:
					rows.addAll(yearRows.get(i).getMonthRows());
					break;
				case DAY:
					rows.addAll(yearRows.get(i).getDayRows());
					break;
			}
		}

		return rows;
	}

	/**
	 * Create a new year and put it on the yearly map
	 */
	private void createNextYear() {
		if (yearRows.isEmpty()) {
			int firstInvestmentYear = movements.firstKey().getYear();
			yearRows.put(firstInvestmentYear, new YearPoint(firstInvestmentYear));
		} else {
			YearPoint year = yearRows.lastEntry().getValue().rollNextYear();
			yearRows.put(yearRows.lastKey(), year);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	private class YearPoint implements DataPoint {

		private final static int LAST_MONTH_NUMBER = 11;

		private final MonthPoint[] months = new MonthPoint[12];

		private double investedPeriod = 0;

		private double netGainPeriod = 0;

		private final int year;

		YearPoint(int year) {
			this.year = year;

			months[0] = new MonthPoint(year, 1);
			investedPeriod = months[0].getInvestedPeriod();
			netGainPeriod = months[0].getNetGainPeriod();

			for (int i = 1; i < 12; i++) {
				months[i] = months[i - 1].rollNextMonth();
				investedPeriod += months[i].getInvestedPeriod();
				netGainPeriod += months[i].getNetGainPeriod();
			}
		}

		private YearPoint(int year, MonthPoint lastMonth) {
			this.year = year;

			for (int i = 0; i < 12; i++) {
				lastMonth = lastMonth.rollNextMonth();
				months[i] = lastMonth;
				netGainPeriod += months[i].getNetGainPeriod();
				investedPeriod += months[i].getInvestedPeriod();
			}
		}

		/**
		 * Create next year using this one as a basis
		 *
		 * @return
		 */
		YearPoint rollNextYear() {
			return new YearPoint(year + 1, getLastMonth());
		}

		@Override
		public String getIdentification() {
			return String.valueOf(year);
		}

		@Override
		public double getInvestedPeriod() {
			return investedPeriod;
		}

		@Override
		public double getNetGainPeriod() {
			return netGainPeriod;
		}

		@Override
		public double getInvestedTotal() {
			return getLastMonth().getInvestedTotal();
		}

		@Override
		public double getNetGainTotal() {
			return getLastMonth().getNetGainTotal();
		}

		@Override
		public double getAvailable() {
			return getLastMonth().getAvailable();
		}

		@Override
		public boolean hasMoreData() {
			return true;
		}

		private MonthPoint getLastMonth() {
			return months[LAST_MONTH_NUMBER];
		}

		/**
		 * TODO For testing of proof of concept, probably will be deprecated/deleted
		 * <p/>
		 * Get daily rows from year.
		 *
		 * @return
		 */
		List<DataPoint> getDayRows() {
			List<DataPoint> rows = new ArrayList<>();

			for (int i = 0; i < months.length; i++) {
				rows.addAll(months[i].getDayRows());
			}

			return rows;
		}

		/**
		 * TODO For testing of proof of concept, probably will be deprecated/deleted
		 * <p/>
		 * Get monthly rows from year.
		 *
		 * @return
		 */
		List<DataPoint> getMonthRows() {
			return new ArrayList<DataPoint>(Arrays.asList(months));
		}
	}

	private class MonthPoint implements DataPoint {

		private final int year;

		private final int month;

		private final int lastDay;

		private double investedPeriod = 0;

		private double netGainPeriod = 0;

		private List<DayPoint> unusedDays = new ArrayList<>();

		private Map<Integer, DayPoint> days = new TreeMap<>();

		MonthPoint(int year, int month) {
			this.year = year;
			this.month = month;

			Calendar cal = getStartOfMonth();
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			DayPoint yesterday = null;
			for (int day = 1; day <= lastDay; day++) {
				LocalDate date = LocalDate.of(year, month, day);
				yesterday = new DayPoint(date, yesterday);
				days.put(day, yesterday);

				investedPeriod += yesterday.getInvestedPeriod();
				netGainPeriod += yesterday.getNetGainPeriod();
			}
		}

		private MonthPoint(int year, int month, MonthPoint lastMonth) {
			this.year = year;
			this.month = month;
			this.unusedDays = new ArrayList<>(lastMonth.days.values());

			Calendar cal = getStartOfMonth();
			LocalDate date = LocalDate.of(year, month, 1);

			DayPoint yesterday = unusedDays.get(unusedDays.size() - 1);
			yesterday = new DayPoint(date, yesterday, unusedDays.remove(0), lastMonth.unusedDays);
			days.put(1, yesterday);

			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int day = 2; day <= lastDay; day++) {
				investedPeriod += yesterday.getInvestedPeriod();
				netGainPeriod += yesterday.getNetGainPeriod();

				date = LocalDate.of(year, month, day);

				if (unusedDays.size() > 0) {
					yesterday = new DayPoint(date, yesterday, unusedDays.remove(0));
				} else {
					yesterday = new DayPoint(date, yesterday);
				}

				days.put(day, yesterday);
			}

			investedPeriod += yesterday.getInvestedPeriod();
			netGainPeriod += yesterday.getNetGainPeriod();
		}

		/**
		 * Creates next month using the actual as a base
		 *
		 * @return
		 */
		MonthPoint rollNextMonth() {
			int nextMonth = (month % 12) + 1;
			int newYear = nextMonth == 1 ? year + 1 : year;

			return new MonthPoint(newYear, nextMonth, this);
		}


		/**
		 * TODO For testing of proof of concept, probably will be deprecated/deleted
		 * <p/>
		 * Get daily rows from month.
		 *
		 * @return
		 */
		List<DataPoint> getDayRows() {
			return new ArrayList<DataPoint>(days.values());
		}

		/**
		 * Creates a calendar with the start of the month as set time.
		 *
		 * @return
		 */
		private Calendar getStartOfMonth() {
			Calendar cal = new GregorianCalendar();
			cal.clear();
			cal.set(year, month, 1);

			return cal;
		}

		@Override
		public String getIdentification() {
			return String.format("%d/%d", month, year);
		}

		@Override
		public double getInvestedPeriod() {
			return investedPeriod;
		}

		@Override
		public double getNetGainPeriod() {
			return netGainPeriod;
		}

		@Override
		public double getInvestedTotal() {
			return days.get(lastDay).investedTotal;
		}

		@Override
		public double getNetGainTotal() {
			return days.get(lastDay).netGainTotal;
		}

		@Override
		public double getAvailable() {
			return days.get(lastDay).getAvailable();
		}

		@Override
		public boolean hasMoreData() {
			return true;
		}
	}

	private class DayPoint implements DataPoint {
		private final LocalDate date;

		private double onDay = 0;

		private double netGainTotal = 0;

		private double investedTotal = 0;

		private double netGain = 0;

		private double invested = 0;

		DayPoint(LocalDate date) {
			this.date = date;

			LocalDate today = LocalDate.now();
			if (today.isAfter(date)) {
				computeMovement(MonthlyInvestment.this.movements.get(date));
				return;
			}

			for (Map.Entry<Recurrence, Movement> recurrentEntry : recurrent.entrySet()) {
				if (recurrentEntry.getKey().matchs(today)) {
					computeMovement(recurrentEntry.getValue());
				}
			}
		}

		DayPoint(LocalDate date, DayPoint yesterday) {
			this(date);

			if (yesterday != null) {
				investedTotal += yesterday.investedTotal;
				netGainTotal = yesterday.netGainTotal;
			}
		}

		DayPoint(LocalDate date, DayPoint yesterday, DayPoint lastMonthDay) {
			this(date, yesterday);

			netGain = lastMonthDay.onDay * monthlyInterest;
			onDay += lastMonthDay.onDay + netGain;
			netGainTotal += netGain;
		}

		DayPoint(LocalDate date, DayPoint yesterday, DayPoint lastMonthDay,
				List<DayPoint> lastMonthRelatives) {
			this(date, yesterday, lastMonthDay);

			double relativeNetGain = 0;
			for (DayPoint row : lastMonthRelatives) {
				relativeNetGain += row.onDay * monthlyInterest;
			}

			onDay += relativeNetGain;
			netGain += relativeNetGain;
			netGainTotal += relativeNetGain;
		}

		/**
		 * Computes the current movement and set the Data Point parameters accordingly
		 *
		 * @param movement Movement to be computed
		 */
		private void computeMovement(Movement movement) {
			if (movement != null) {
				invested = movement.getValue();
				investedTotal = invested;
				onDay = invested;
			}
		}

		@Override
		public String getIdentification() {
			return String
					.format("%d/%d/%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		}

		@Override
		public double getInvestedPeriod() {
			return invested;
		}

		@Override
		public double getNetGainPeriod() {
			return netGain;
		}

		@Override
		public double getInvestedTotal() {
			return investedTotal;
		}

		@Override
		public double getNetGainTotal() {
			return netGainTotal;
		}

		@Override
		public double getAvailable() {
			return netGainTotal + investedTotal;
		}

		@Override
		public boolean hasMoreData() {
			return false;
		}
	}
}
