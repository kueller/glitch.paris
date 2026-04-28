package com.glitch.rest

import com.glitch.config.appConfig
import com.glitch.model.Document
import com.glitch.util.concat
import org.http4k.core.*
import org.http4k.lens.contentType
import org.http4k.routing.path
import java.io.File
import kotlin.io.path.exists
import kotlin.io.path.pathString


val documentHandler: HttpHandler = { request: Request ->
    val filename: String = request.path("filename") ?: "-"
    val filePath: String = Document.findByFilename(filename)?.path ?: "-"
    val fullPath = appConfig.externPath.concat(filePath)

    if (fullPath.exists()) {
        try {
            File(fullPath.pathString).inputStream().let {
                Response(Status.OK)
                    .header("Content-Disposition", "Inline; ${fullPath.fileName.pathString}")
                    .contentType(MimeTypes().forFile(fullPath.pathString))
                    .body(it)
            }
        } catch (e: Exception) {
            Response(Status.NOT_FOUND)
        }
    } else {
        Response(Status.NOT_FOUND)
    }
}