package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "paging_keys", primaryKeys = ["api"])
data class PagingKeys(
    @ColumnInfo(name = "api") val api: String,
    @ColumnInfo(name = "page") val page: Int?
)