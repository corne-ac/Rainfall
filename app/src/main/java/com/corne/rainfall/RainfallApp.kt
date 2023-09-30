package com.corne.rainfall

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [RainfallApp] is a custom [Application] class for the Rainfall app.
 *
 * This class is annotated with [@HiltAndroidApp] to enable Hilt for dependency injection
 * throughout the application. It serves as the entry point of the Rainfall app and
 * is automatically created and managed by the Android system.
 *
 * This class is used to initialize application-wide resources, set up Hilt components,
 * and perform other necessary tasks when the app starts.
 */
@HiltAndroidApp
class RainfallApp : Application()

