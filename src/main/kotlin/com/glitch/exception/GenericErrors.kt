package com.glitch.exception

import org.http4k.core.Status

class ParsingError(error: String) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "Error reading JSON body: $error."
}

class NotFound : GlitchException() {
    override val status = Status.NOT_FOUND
    override val message: String = "Not found"
}

class BadFormatting(field: Any) : GlitchException() {
    override val status = Status.BAD_REQUEST
    override val message: String = "Incorrect formatting: $field."
}
