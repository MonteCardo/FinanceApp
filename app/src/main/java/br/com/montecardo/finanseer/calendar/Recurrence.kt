package br.com.montecardo.finanseer.calendar

import org.threeten.bp.LocalDate

/**
 * Matcher for a recurrence, says if the date conform with the rules.
 *
 * Created by gabryel on 22/04/16.
 */
interface Recurrence {

    /**
     * Allows checking if the given date matches with the parameters of the recurrence
     *
     * @param date
     * @return
     */
    fun matchs(date: LocalDate): Boolean
}
