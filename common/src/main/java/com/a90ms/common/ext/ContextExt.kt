package com.a90ms.common.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

fun Context.quit() {
    if (this is Activity) {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        exitProcess(0)
    }
}

fun Context?.isValidContext(): Boolean = when (this) {
    null -> false
    is Activity -> (isDestroyed || isFinishing).not()
    else -> true
}

fun Context.openBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}
