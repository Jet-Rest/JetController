package cn.codetector.jet.jetcontroller.util

import java.text.DecimalFormat

/**
 * Created by Codetector on 2017/3/11.
 * Project Jet
 */
val units: Array<String> = arrayOf("B", "kB", "MB", "GB", "TB")

fun Long.formatSizeLength(): String {
    if (this <= 0) return "0 B"
    val digitGroups: Int = (Math.log10(this.toDouble()) / Math.log10(1024.0)).toInt()
    return DecimalFormat ("#,##0.#").format(this / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
}