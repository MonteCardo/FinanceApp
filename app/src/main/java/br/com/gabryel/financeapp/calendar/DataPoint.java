package br.com.gabryel.financeapp.calendar;

/**
 * Interface defining a point of data for use in a table or graph
 * <p/>
 * Created by gabryel on 20/04/16.
 */
public interface DataPoint {
	/**
	 * Get a identification of the data point to user's view
	 *
	 * @return
	 */
	String getIdentification();

	/**
	 * Get the invested amount on a moment
	 *
	 * @return
	 */
	double getInvestedPeriod();

	/**
	 * Get the net gain on a moment
	 *
	 * @return
	 */
	double getNetGainPeriod();

	/**
	 * Get the total invested until this moment
	 *
	 * @return
	 */
	double getInvestedTotal();

	/**
	 * Get the total net gain until this moment
	 *
	 * @return
	 */
	double getNetGainTotal();

	/**
	 * Get the available quantity of money on a moment
	 *
	 * @return
	 */
	double getAvailable();

	/**
	 * Says if the node is a leaf node or if there is more data inside it
	 *
	 * @return
	 */
	boolean hasMoreData();
}
