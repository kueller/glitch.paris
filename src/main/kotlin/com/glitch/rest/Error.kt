package com.glitch.rest

import org.http4k.core.*


data class ErrorPage(val status: Int, val description: String, val path: String, val details: String? = null) :
    BasicViewModel()


fun errorProcessor(status: Int, description: String, details: String? = null): HTMLPrepare = { request: Request ->
    ErrorPage(status, description, request.uri.path, details).also { it.templatePath = "/error" }
}


val errorPageFilter: Filter = { next: HttpHandler ->
    { request: Request ->
        try {
            next(request).let { response: Response ->
                when (response.status.code) {
                    in Status.CLIENT_ERROR, in Status.SERVER_ERROR -> {
                        HTMLPage()
                            .from(errorProcessor(response.status.code, response.status.description))
                            .apply {
                                defaultStatus = response.status
                            }.invoke(request)
                    }

                    else -> response
                }
            }
        } catch (e: Exception) {
            val error: String = e.toString().substringAfterLast('.')
            println(e)
            HTMLPage()
                .from(errorProcessor(500, "Internal Server Error", error))
                .apply {
                    defaultStatus = Status(500, error)
                }.invoke(request)
        }
    }
}