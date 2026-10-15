package com.glitch.rest.web.shows

import com.glitch.exception.NotFound
import com.glitch.model.shows.*
import com.glitch.rest.web.BasicViewModel
import com.glitch.rest.web.HTMLPrepare
import com.glitch.util.COUNTRY_DISPLAY_NAME
import com.glitch.util.COUNTRY_TO_FLAG
import org.http4k.core.Request
import org.http4k.routing.path
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

data class BandShowModel(
    val date: String,
    val city: String,
    val country: String,
    val isHeadliner: Boolean,
    val title: String? = null,
)

data class BandPageModel(
    val bandName: String,
    val country: String,
    val genre: String,
    val headliningCount: Int,
    val shows: List<BandShowModel>,
    val lastfmUrl: String? = null,
    val countryStatText: String? = null,
) : BasicViewModel()

val bandInfo: HTMLPrepare = { request: Request ->
    val uuid: String = request.path("uuid") ?: "-"
    val query = Band.getBandAndShowsByUuid(uuid)

    transaction {
        if (query.empty()) throw NotFound()

        val band = Band.wrapRows(query).toList().first()
        val countryStats = mutableMapOf<String, Int>()
        val shows = query.map { row ->
            val event = Event.wrapRow(row)
            val performance = BandEvent.wrapRow(row)
            val venue = Venue.wrapRow(row)

            val countryCount: Int = when {
                venue.country in countryStats -> countryStats[venue.country] ?: 0
                else -> 0
            }

            countryStats[venue.country] = countryCount + 1

            BandShowModel(
                date = performance.performanceDate.toString(),
                city = venue.city,
                country = venue.country,
                isHeadliner = performance.headliner,
                title = event.eventName ?: event.bandEvents.find {
                    it.headliner
                }?.band?.let {
                    when (it.uuid) {
                        band.uuid -> null
                        else -> it.name
                    }
                }
            )
        }

        BandPageModel(
            bandName = band.name,
            country = band.country?.let { "$it ${COUNTRY_TO_FLAG[it]}" } ?: "",
            genre = Genre.valueOf(band.genre ?: "").displayName,
            headliningCount = shows.count { it.isHeadliner },
            shows = shows,
            lastfmUrl = band.lastfmUrl,
            countryStatText = when (countryStats.size) {
                0, 1 -> null
                else -> countryStats
                    .toList()
                    .sortedByDescending { it.second }
                    .joinToString(", ") { (key, value) ->
                        "$value in ${COUNTRY_DISPLAY_NAME[key]} ${COUNTRY_TO_FLAG[key]}"
                    }
            }
        )
    }
}