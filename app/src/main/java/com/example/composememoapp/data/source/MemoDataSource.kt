package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo

interface MemoDataSource {
    interface LoadMemoListCallback{
        fun onMemoListLoaded(list : List<Memo>)
        fun onDataNotAvailable()
    }

    suspend fun loadMemoList(callback : LoadMemoListCallback)

    suspend fun addMemo(memo : Memo)
}