package com.glitch.config

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.http4k.format.ConfigurableMoshi
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings

object LocalDateAdapter {
    @ToJson
    fun toJson(date: LocalDate?): String? =
        when {
            date == null -> null
            else -> date.toString()
        }

    @FromJson
    fun fromJson(date: String?): LocalDate? =
        when {
            date.isNullOrBlank() -> null
            else -> LocalDate.parse(date)
        }
}

object LocalTimeAdapter {
    @ToJson
    fun toJson(time: LocalTime): String = time.toString()

    @FromJson
    fun fromJson(time: String): LocalTime = LocalTime.parse(time)
}

object GlitchMoshi : ConfigurableMoshi(
    Moshi.Builder()
        .add(LocalDateAdapter)
        .add(LocalTimeAdapter)
        .asConfigurable()
        .withStandardMappings()
        .done()
)