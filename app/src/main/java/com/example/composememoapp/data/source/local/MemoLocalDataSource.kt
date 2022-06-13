package com.example.composememoapp.data.source.local

import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource

class MemoLocalDataSource(private val memoDao : MemoDao) {

    fun loadMemoList() = memoDao.getMemoList()

    suspend fun addMemo(memo: Memo) = memoDao.insertMemo(memo)

    suspend fun modifyMemo(memo : Memo) = memoDao.modifyMemo(memo)
}