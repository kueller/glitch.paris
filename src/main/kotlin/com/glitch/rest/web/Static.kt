package com.glitch.rest.web

import com.glitch.config.Environments.Companion.PROD
import com.glitch.config.appConfig
import com.glitch.rest.filter.fontAccessFilter
import com.glitch.util.concat
import org.http4k.core.*
import org.http4k.lens.contentType
import org.http4k.routing.ResourceLoader
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.ResourceLoader.Companion.Directory
import org.http4k.routing.static
import java.net.URL
import kotlin.io.path.pathString


internal class StaticDirectory {
    companion object {
        val localStatic: ResourceLoader = when (appConfig.env) {
            PROD -> Classpath("static")
            else -> Directory(appConfig.projectResources.concat("static").pathString)
        }
        val exteralStatic: ResourceLoader = Directory(appConfig.externPath.concat("static").pathString)
    }
}


/**
 * In production, will first search the static classpath directory.
 * If the file is not found, it will search the EXTERN_PATH/static directory.
 *
 * For other environments it will read the classpath static using the full path
 * PROJECT_RESOURCES_PATH/static to allow for hot reloading.
 */
val staticHandler: HttpHandler = { request: Request ->
    val path = request.uri.path.removePrefix("/static/")

    (StaticDirectory.localStatic.load(path)?.let {
        StaticDirectory.localStatic
    } ?: StaticDirectory.exteralStatic).let { loader: ResourceLoader ->
        static(loader)
            .withBasePath("/static")
            .withFilter(fontAccessFilter)
            .invoke(request)
    }
}


val favicon: HttpHandler = { request: Request ->
    try {
        Classpath(muteWarning = true).load("favicon.ico")?.let { url: URL ->
            Response(Status.OK)
                .contentType(MimeTypes().forFile(url.path))
                .body(url.openStream())
        } ?: Response(Status.NOT_FOUND)
    } catch (e: Exception) {
        Response(Status.NOT_FOUND)
    }
}