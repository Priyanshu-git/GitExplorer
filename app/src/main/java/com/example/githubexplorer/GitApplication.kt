package com.example.githubexplorer

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class GitApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}