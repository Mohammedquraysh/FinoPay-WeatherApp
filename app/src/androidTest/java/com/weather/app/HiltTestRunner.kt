package com.weather.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom test runner that replaces the application with [HiltTestApplication]
 * so Hilt can inject dependencies in instrumented tests.
 *
 * Referenced in app/build.gradle.kts:
 *   testInstrumentationRunner = "com.weather.app.HiltTestRunner"
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application = super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
