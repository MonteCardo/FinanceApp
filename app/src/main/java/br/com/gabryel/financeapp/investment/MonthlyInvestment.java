package br.com.gabryel.financeapp.investment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Simple representation of an monthly investment.
 * <p/>
 * Created by gabryel on 04/04/16.
 */
public class MonthlyInvestment implements Investment {

    private final List<Movement> movements = new ArrayList<>();

    private final double monthlyInterest;

    private final String name;

    public MonthlyInvestment(String name, double monthlyInterest) {
        this.name = name;
        this.monthlyInterest = monthlyInterest;
    }

    @Override
    public void add(Movement movement) {
        movements.add(movement);
    }

    @Override
    public double getAvailableMoneyOn(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        double value = 0;

        for (Movement movement : movements) {
            if (date.after(movement.getMovementDate())) {
                value += applyInterest(movement, calendar);
            }
        }

        return value;
    }

    @Override
    public double getInvestedMoneyOn(Date date) {
        double value = 0;

        for (Movement movement : movements) {
            if (date.after(movement.getMovementDate()))
                value += movement.getValue();
        }

        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Apply interest to movement until it reaches desired date
     *
     * @param movement
     * @param calendar
     * @return Value with interest applied
     */
    private double applyInterest(Movement movement, Calendar calendar) {
        Calendar movCalendar = new GregorianCalendar();
        movCalendar.setTime(movement.getMovementDate());
        movCalendar.add(Calendar.MONTH, 1);

        double value = movement.getValue();
        while (calendar.after(movCalendar)) {
            movCalendar.add(Calendar.MONTH, 1);
            value *= monthlyInterest;
        }

        return value;
    }
}
