package com.glitch.util

import org.http4k.core.Request
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.SameSite

const val COOKIE_AGE: Long = 60 * 60 * 24 * 30 * 6

fun createCookie(key: String, value: String, maxAge: Long = COOKIE_AGE): Cookie =
    Cookie(
        name = key,
        value = value,
        maxAge = maxAge,
        path = "/",
        secure = true,
        httpOnly = true,
        sameSite = SameSite.Strict,
    )

