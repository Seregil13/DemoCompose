package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.PagingKeys

@Dao
interface PagingKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pages: PagingKeys)

    @Query("SELECT * FROM paging_keys WHERE api = :api")
    suspend fun remotePageForApi(api: String): PagingKeys

    @Query("DELETE FROM paging_keys WHERE api = :api")
    suspend fun deleteByApi(api: String)

    @Query("SELECT * FROM paging_keys WHERE page = :i")
    suspend fun pageByNumber(i: Int): PagingKeys
}