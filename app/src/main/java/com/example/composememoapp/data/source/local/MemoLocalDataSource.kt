package com.example.composememoapp.data.source.local

import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource

class MemoLocalDataSource(private val memoDao : MemoDao) : MemoDataSource {
    override suspend fun loadMemoList(callback: MemoDataSource.LoadMemoListCallback) {
        val memoList = memoDao.getMemoList()
        callback.onMemoListLoaded(memoList)
    }

    override suspend fun addMemo(memo: Memo) {
        memoDao.insertMemo(memo)
    }

}