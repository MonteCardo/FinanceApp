package br.com.gabryel.financeapp.investment

import org.threeten.bp.LocalDate

import br.com.gabryel.financeapp.calendar.DataPoint
import br.com.gabryel.financeapp.calendar.Periodicity

/**
 * Interface used to define what are the minimum requirements of an investment
 *
 * Created by gabryel on 04/04/16.
 */
interface Investment : Moneyable {

    /**
     * Add a movement to this investment
     *
     * @param movement Movement with the needed data
     */
    fun add(movement: Movement)

    /**
     * Create rows until given date
     *
     * @param to        Representation of the last asked day
     * @param type      Periodicity of the info on a row
     *
     * @return
     */
    fun getRows(type: Periodicity, to: LocalDate): List<DataPoint>

    /**
     * Create rows from certain date to another
     *
     * @param from        Representation of the first asked day
     * @param to        Representation of the last asked day
     * @param type      Periodicity of the info on a row
     *
     * @return
     */
    fun getRows(type: Periodicity, from: LocalDate, to: LocalDate): List<DataPoint>
}
