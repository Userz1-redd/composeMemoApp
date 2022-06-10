package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.local.MemoLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MemoRepository(private val local : MemoLocalDataSource) : MemoDataSource {
    override suspend fun loadMemoList() = local.loadMemoList().flowOn(Dispatchers.IO).conflate()

    override suspend fun addMemo(memo: Memo) {
        local.addMemo(memo)
    }

}