package com.glitch.rest

import com.glitch.model.Admin
import com.glitch.security.createJwt
import com.glitch.security.hashPassword
import com.glitch.security.verifyJwt
import com.glitch.util.createCookie
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.replaceCookie


val adminOnly: Filter = { next: HttpHandler ->
    { request: Request ->
        val token = request.cookie("jwt")?.value

        val decoded = token?.let { verifyJwt(token) }

        val user = decoded?.getClaim("user")?.asString()
        val subject = decoded?.subject

        when {
            user == "admin" && subject == "showsManager" -> next(request)
            else -> {
                Response(Status.FOUND)
                    .header("Location", "/login-admin?return=${request.uri.path}")
            }
        }
    }
}


val verifyAdmin: Filter = { next: HttpHandler ->
    { request: Request ->
        val formParams: Map<String, String?> = request.form().toMap()
        for ((k, v) in formParams) {
            println("$k: ${v ?: "null"}")
        }

        val password = formParams["txt_password"]?.trim() ?: "null"
        val adminCredentials = Admin.getAdminCredentials()

        val hashed = hashPassword(password, adminCredentials?.salt ?: "salt")

        when (hashed) {
            adminCredentials?.password -> {
                val jwt = createJwt("showsManager", "admin")
                val redirectUri = formParams["txt_return"] ?: request.uri.path

                Response(Status.FOUND)
                    .header("Location", redirectUri)
                    .cookie(createCookie("jwt", jwt, 3600))
            }
            else -> next(request)
        }
    }
}