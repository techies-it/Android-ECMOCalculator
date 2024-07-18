package com.ecmocalc.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.Locale


class Helper {
    companion object {
        fun convertStringToDouble(input: String): Double? {
            return try {
                // Try to parse the string to a Double
                val doubleValue = input.toDouble()

                // Convert the double to a string with formatted output
                val formattedValue =
                    String.format(Locale.ROOT, "%.15f", doubleValue).trimEnd('0').trimEnd('.')

                // If the string representation starts with a dot, add a zero before it
                if (formattedValue.startsWith(".")) {
                    "0$formattedValue".toDouble()
                } else {
                    doubleValue
                }
            } catch (e: NumberFormatException) {
                // Return null if the string can't be converted to a double
                null
            }
        }

        fun getNavigationMode(context: Context): Boolean {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 (API 29) and above
                return Settings.Secure.getInt(context.contentResolver, "navigation_mode", -1) == 2
            } else {
                // Below Android 10
                return false // Gesture navigation is not supported
            }
        }

        fun isEdgeToEdgeEnabled(context: Context): Int {
            val resources = context.resources
            val resourceId =
                resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
            if (resourceId > 0) {
                return resources.getInteger(resourceId)
            }
            return 0
        }
    }
}