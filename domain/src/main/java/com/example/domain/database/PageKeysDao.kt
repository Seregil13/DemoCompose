package com.example.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.database.entity.PageKeys

@Dao
interface PageKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pages: PageKeys)

    @Query("SELECT * FROM pages WHERE api = :api")
    suspend fun remotePageForApi(api: String): PageKeys

    @Query("DELETE FROM pages WHERE api = :api")
    suspend fun deleteByApi(api: String)

}