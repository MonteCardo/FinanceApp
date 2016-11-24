package br.com.gabryel.financeapp.investment

import org.threeten.bp.LocalDate

/**
 * Simple implementation of a movement.
 *
 * Created by gabryel on 04/04/16.
 */
class SimpleMovement : Movement {

    override val movementDate: LocalDate

    override val value: Double

    constructor(movementDate: LocalDate, vararg movements: Movement) {
        this.movementDate = movementDate
        var partialValue = 0.0

        for (mov in movements) {
            partialValue += mov.value
        }

        this.value = partialValue
    }

    constructor(movementDate: LocalDate, value: Double) {
        this.movementDate = movementDate
        this.value = value
    }
}
