package com.example.composememoapp.data.source.local

import androidx.room.*
import com.example.composememoapp.data.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getMemoList(): Flow<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo : Memo)

    @Update
    suspend fun modifyMemo(memo : Memo)
}