package com.glitch.rest.web.shows

import com.glitch.model.shows.Genre
import com.glitch.rest.web.BasicViewModel
import com.glitch.rest.web.HTMLPrepare
import kotlin.enums.EnumEntries

data class ManageModel(val genres: EnumEntries<Genre>) : BasicViewModel()

val manage: HTMLPrepare = { _ ->
    ManageModel(Genre.entries)
}