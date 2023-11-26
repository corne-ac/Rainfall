package com.corne.rainfall.utils

import androidx.room.TypeConverter
import java.util.Date

/**
 * This object provides type converters for the Room database.
 * It provides methods to convert a Date to a Long (timestamp) and vice versa.
 * These methods are annotated with @TypeConverter to indicate that they are type converters for Room.
 */
object DateConverter {
    /**
     * This method converts a Date to a Long (timestamp).
     *
     * @param date The Date to be converted.
     * @return The Long representation of the date.
     */
    @TypeConverter
    fun timestampFromDate(date: Date): Long {
        return date.time
    }

    /**
     * This method converts a Long (timestamp) to a Date.
     *
     * @param timestamp The Long to be converted.
     * @return The Date representation of the timestamp.
     */
    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date {
        return Date(timestamp)
    }
}