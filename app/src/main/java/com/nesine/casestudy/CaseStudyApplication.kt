package com.nesine.casestudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CaseStudyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}