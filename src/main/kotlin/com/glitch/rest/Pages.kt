package com.glitch.rest

import com.glitch.model.NightImage
import org.http4k.core.Request

data class NightModel(val images: List<NightImage>) : BasicViewModel()

val hello: HTMLPrepare = { request: Request -> BasicViewModel() }

val night: HTMLPrepare = { request: Request ->
    val images = NightImage.getAll()
    NightModel(images)
}