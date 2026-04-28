package com.glitch

import com.glitch.config.appConfig
import com.glitch.rest.restRouting
import org.http4k.core.HttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.server.uri
import org.jetbrains.exposed.v1.jdbc.Database


/**
 * The main entry point to routing. No other routes should be here.
 */
val appRouting: HttpHandler = routes(
    "/" bind restRouting,
)


fun main() {
    println("Starting glitch.paris...")
    val app: HttpHandler = appRouting

    println("Using config: \"${appConfig.env}\"...")

    println("Connecting to database ${appConfig.dbUri}...")
    Database.connect(
        url = appConfig.dbUri,
        user = appConfig.dbUser,
        password = appConfig.dbPassword,
    )

    println("Starting server...")
    val server = app.asServer(Jetty(appConfig.serverPort)).start()

    println("Server started on ${server.uri()}")
    server.block()
}
