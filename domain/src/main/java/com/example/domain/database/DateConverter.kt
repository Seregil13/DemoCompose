package com.example.domain.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DateConverter {
    @TypeConverter
    fun dateToString(value: Instant?): String? {
        return value?.let { Json.encodeToString(value) }
    }

    @TypeConverter
    fun dateFromString(value: String?): Instant? {
        return value?.let { Json.decodeFromString(value) }
    }

    @TypeConverter
    fun localDateToString(value: LocalDate?): String? {
        return value?.let { Json.encodeToString(value) }
    }

    @TypeConverter
    fun localDateFromString(value: String?): LocalDate? {
        return value?.let { Json.decodeFromString(value) }
    }
}
