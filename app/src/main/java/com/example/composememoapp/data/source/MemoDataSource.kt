package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo
import kotlinx.coroutines.flow.Flow

interface MemoDataSource {

    suspend fun loadMemoList() : Flow<List<Memo>>

    suspend fun addMemo(memo : Memo)

    suspend fun modifyMemo(memo : Memo)
}