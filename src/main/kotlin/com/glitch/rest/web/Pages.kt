package com.glitch.rest.web

import com.glitch.model.NightImage
import org.http4k.core.Request

data class NightModel(val images: List<NightImage>) : BasicViewModel()

data class LoginRedirectModel(val returnUri: String) : BasicViewModel()

val hello: HTMLPrepare = { _: Request -> BasicViewModel() }

val night: HTMLPrepare = { _: Request ->
    val images = NightImage.getAll()
    NightModel(images)
}

val login: HTMLPrepare = { request: Request ->
    LoginRedirectModel(request.query("return") ?: "")
}