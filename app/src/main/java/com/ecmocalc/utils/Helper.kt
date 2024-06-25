package com.ecmocalc.utils

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
    }
}