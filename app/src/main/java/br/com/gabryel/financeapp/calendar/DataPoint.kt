package br.com.gabryel.financeapp.calendar

/**
 * Interface defining a point of data for use in a table or graph
 *
 * Created by gabryel on 20/04/16.
 */
interface DataPoint {
    /**
     * Get a identification of the data point to user's view
     *
     * @return
     */
    val identification: String
        get

    /**
     * Get the invested amount on a moment
     *
     * @return
     */
    var investedPeriod: Double
        get

    /**
     * Get the net gain on a moment
     *
     * @return
     */
    var netGainPeriod: Double
        get

    /**
     * Get the total invested until this moment
     *
     * @return
     */
    var investedTotal: Double
        get

    /**
     * Get the total net gain until this moment
     *
     * @return
     */
    var netGainTotal: Double
        get

    /**
     * Get the available quantity of money on a moment
     *
     * @return
     */
    var available: Double
        get

    /**
     * Says if the node is a leaf node or if there is more data inside it
     *
     * @return
     */
    fun hasMoreData(): Boolean
}
