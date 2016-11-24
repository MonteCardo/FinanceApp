package br.com.gabryel.financeapp.investment;

import org.threeten.bp.LocalDate;

import java.util.List;

import br.com.gabryel.financeapp.calendar.DataPoint;
import br.com.gabryel.financeapp.calendar.Periodicity;

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
	 * Create rows until given date
	 *
	 * @param to		Representation of the last asked day
	 * @param type      Periodicity of the info on a row
	 * @return
	 */
	List<DataPoint> getRows(Periodicity type, LocalDate to);

	/**
	 * Create rows from certain date to another
	 *
	 * @param from		Representation of the first asked day
	 * @param to		Representation of the last asked day
	 * @param type      Periodicity of the info on a row
	 * @return
	 */
	List<DataPoint> getRows(Periodicity type, LocalDate from, LocalDate to);
}
