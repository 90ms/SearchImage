package com.a90ms.common.ext

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

fun Context.quit() {
    if (this is Activity) {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        exitProcess(0)
    }
}
