package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.local.MemoLocalDataSource

class MemoRepository(private val local : MemoLocalDataSource) : MemoDataSource {
    override suspend fun loadMemoList(callback: MemoDataSource.LoadMemoListCallback) {
        local.loadMemoList(object : MemoDataSource.LoadMemoListCallback{
            override fun onMemoListLoaded(list: List<Memo>) {
                callback.onMemoListLoaded(list)
            }
            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override suspend fun addMemo(memo: Memo) {
        local.addMemo(memo)
    }

}