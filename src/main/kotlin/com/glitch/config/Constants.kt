package com.glitch.config


/**
 * The top level pages on the site mapped to the URI path.
 */
enum class TopLevelPage(val entry: Pair<String, String>) {
    HOME("home" to "/"),
    KNOWLEDGE("knowledge" to "https://knowledge.glitch.paris"),
    NIGHT("night" to "/night"),
    ETC("etc" to "/etc");

    val pageName: String = this.entry.first
    val pagePath: String = this.entry.second
}


sealed class Environments {
    companion object {
        const val PROD = "prod"
        const val DEV = "dev"
        const val TEST = "test"
    }
}