package com.example.composememoapp.data.source.local

import androidx.room.*
import com.example.composememoapp.data.Memo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getMemoList(): Flowable<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemo(memo : Memo) : Completable

    @Update
    fun modifyMemo(memo : Memo) : Completable
}