package com.example.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

class InstantConverter {
    @TypeConverter
    fun dateToString(value: Instant?): String? {
        return value?.let { value.toString() }
    }

    @TypeConverter
    fun dateFromString(value: String?): Instant? {
        return value?.let { value.toInstant() }
    }
}