package com.glitch.exception

import org.http4k.core.Status

abstract class GlitchException : Exception() {
    abstract val status: Status
    abstract override val message: String
}
