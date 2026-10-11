package com.glitch.exception

import kotlinx.datetime.LocalDate
import org.http4k.core.Status

class VenueNotFound(
    uuid: String,
) : GlitchException() {
    override val status = Status.NOT_FOUND
    override val message: String = "Invalid venue ID '$uuid'."
}

class BandNotFound(uuid: String) : GlitchException() {
    override val status = Status.NOT_FOUND
    override val message: String = "Invalid band ID. '$uuid'."
}

class NoHeadliner(date: LocalDate) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "No headliner found on date $date."
}

class MultipleHeadliners(date: LocalDate) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "More than one headliner for date $date"
}

class OutOfRange(date: LocalDate) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "$date is not in range for the given event dates."
}

class InvalidDateRange(endDate: LocalDate) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "$endDate is less than or equal to the start date."
}
