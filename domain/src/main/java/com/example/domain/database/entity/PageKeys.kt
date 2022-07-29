package com.example.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "pages", primaryKeys = ["api"])
data class PageKeys(
    @ColumnInfo(name = "api") val api: String,
    @ColumnInfo(name = "page") val page: Int?
)