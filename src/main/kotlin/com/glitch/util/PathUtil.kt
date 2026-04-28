package com.glitch.util

import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString


/**
 * Connects two paths together regardless or origin and ensures
 * the slahes line up.
 *
 * @param addition The path to concatenate, in string format.
 * @return A Path of the concatenated paths.
 */
fun Path.concat(addition: String): Path {
    return Path(File(this.pathString, addition).absolutePath)
}


/**
 * Connects two paths together regardless or origin and ensures
 * the slahes line up.
 *
 * @param addition The Path to concatenate.
 * @return A Path of the concatenated paths.
 */
fun Path.concat(addition: Path): Path {
    return Path(File(this.pathString, addition.pathString).absolutePath)
}