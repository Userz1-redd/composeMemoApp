package com.example.composememoapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composememoapp.data.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    suspend fun getMemoList(): List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo : Memo)
}