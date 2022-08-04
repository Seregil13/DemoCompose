package com.example.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class LocalDateConverter {
    @TypeConverter
    fun localDateToString(value: LocalDate?): String? {
        return value?.let { value.toString() }
    }

    @TypeConverter
    fun localDateFromString(value: String?): LocalDate? {
        return value?.let { value.toLocalDate() }
    }
}