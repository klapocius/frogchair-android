package com.frogchair.android.common

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class DotApplication : Application() {

    companion object {
        lateinit var context: WeakReference<Context>

        fun getContext(): Context = context.get()!!
    }

    override fun onCreate() {
        context = WeakReference(applicationContext)
        super.onCreate()
    }
}