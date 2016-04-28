package br.com.gabryel.financeapp.investment;

import java.util.List;

import br.com.gabryel.financeapp.calendar.DataPoint;
import br.com.gabryel.financeapp.calendar.Periodicity;
import br.com.gabryel.financeapp.date.LocalDate;

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

	/**
	 * TODO Maybe add an lower bound to this method in the future
	 * <p/>
	 * Create rows until given date
	 *
	 * @param type      Periodicity of the info on a row
	 * @param localDate Representation of the last asked day
	 * @return
	 */
	List<DataPoint> getRowsUntil(Periodicity type, LocalDate localDate);
}
