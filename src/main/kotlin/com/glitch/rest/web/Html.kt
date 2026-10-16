package com.glitch.rest.web

import com.glitch.config.Environments.Companion.PROD
import com.glitch.config.TopLevelPage
import com.glitch.config.appConfig
import com.glitch.rest.filter.trailingSlashFilter
import com.glitch.util.concat
import org.http4k.core.*
import org.http4k.template.ThymeleafTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import kotlin.io.path.Path
import kotlin.io.path.pathString

typealias HTMLPrepare = (Request) -> BasicViewModel

abstract class ThymeleafViewModel : ViewModel {
    abstract val topLevelPages: Array<TopLevelPage>
    abstract var name: String
    abstract var useMins: Boolean
    abstract var templatePath: String

    override fun template(): String {
        if (this.templatePath.replace("/", "") == "")
            this.templatePath = this.name
        return "${this.templatePath}.html"
    }
}

/**
 * View model that will store the HTML variables to be sent to the template
 * using $method.customVariable
 *
 * This base one contains:
 * - full [TopLevelPage] enum
 * - useMins defined in the app config
 * - templatePath (not initialized)
 *
 * If you need more variables, extend this. If not, you can send only this.
 * Or not even that if you're using a [HTMLPage].
 */
open class BasicViewModel : ThymeleafViewModel() {
    override val topLevelPages = TopLevelPage.entries.toTypedArray()
    override var templatePath: String = ""
    override var name: String = ""
    override var useMins: Boolean = appConfig.useMins
}

/**
 * Template for a standard HTML web page. To be used as an [HttpHandler].
 *
 * **By default**, when invoked it will create an HTML template using a
 * [BasicViewModel]. It then returns the HTML page associated with the request
 * path in resources/templates, with HTTP 200.
 *
 * - Override the default view model with [from]
 * - Pass a filter with [withFilter]
 * - Override the default status code with [defaultStatus]
 */
open class HTMLPage : HttpHandler {
    val renderer = when (appConfig.env) {
        PROD -> ThymeleafTemplates().CachingClasspath("templates")
        else -> ThymeleafTemplates().HotReload(appConfig.projectResources.concat("templates").pathString)
    }

    private var filter: Filter = Filter.NoOp

    /**
     * Status code returned with the HTML. Default is 200 OK.
     */
    var defaultStatus = Status.OK

    var prepare: HTMLPrepare = { _: Request -> BasicViewModel() }

    /**
     * @param prepare A function to retrieve a [BasicViewModel] or extension
     * to use for generating the template.
     */
    fun from(prepare: HTMLPrepare): HTMLPage {
        this.prepare = prepare
        return this
    }

    fun withFilter(filter: Filter): HTMLPage {
        this.filter = filter
        return this
    }

    /**
     * Calls the view model function [HTMLPrepare], sets the useMins, based on the
     * app config and returns the result.
     * Override if you need more before returning.
     */
    open fun generateViewModel(request: Request): BasicViewModel {
        val viewModel: BasicViewModel = this.prepare(request)
        return viewModel
    }

    override fun invoke(p1: Request): Response {
        return trailingSlashFilter.then(this.filter).then { it: Request ->
            val viewModel: BasicViewModel = this.generateViewModel(it)
            viewModel.useMins = appConfig.useMins
            val view = Body.viewModel(this.renderer, ContentType.TEXT_HTML).toLens()
            Response(this.defaultStatus).with(view of viewModel)
        }.invoke(p1)
    }
}

/**
 * Template for the top level HTML pages. These should be on the root path (like "/etc")
 * but more importantly will be displayed on the front page and top navigation bar.
 * @param page This [TopLevelPage] will define the template file location
 * @param templateName An optional path to the template file (without .html) that will override the
 * automatic method.
 * and the name of the current page.
 */
class HTMLMainPage(val page: TopLevelPage, val templateName: String? = null) : HTMLPage() {
    override fun generateViewModel(request: Request): BasicViewModel {
        val viewModel: BasicViewModel = this.prepare(request)
        viewModel.templatePath = templateName ?: this.page.pagePath
        viewModel.name = this.page.pageName

        return viewModel;
    }
}

/**
 * Template for any other HTML page not considered "top level". For those use [HTMLMainPage].
 * @param templateName The path to the HTML template. Null by default and system searches by
 * the url path. If it starts with a slash "/" it will be read as a full path. Without a slash
 * it is read as a relative path to the parent url location. Do not append ".html" to the path.
 */
class HTMLSubPage(val templateName: String? = null) : HTMLPage() {
    override fun generateViewModel(request: Request): BasicViewModel {
        val requestPath = Path(request.uri.path)

        val viewModel: BasicViewModel = this.prepare(request)

        if (this.templateName.isNullOrBlank()) {
            viewModel.templatePath = requestPath.pathString
        } else if (this.templateName.startsWith("/")) {
            viewModel.templatePath = this.templateName
        } else {
            viewModel.templatePath = requestPath.parent.concat(this.templateName).pathString
        }

        return viewModel;
    }
}