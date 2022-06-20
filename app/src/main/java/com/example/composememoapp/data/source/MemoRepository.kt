package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.local.MemoLocalDataSource
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MemoRepository(private val local : MemoLocalDataSource) : MemoDataSource {
    override fun loadMemoList() = local.loadMemoList()


    override fun addMemo(memo: Memo) : Completable {
        return local.addMemo(memo)
    }

    override fun modifyMemo(memo: Memo) : Completable{
        return local.modifyMemo(memo)
    }

}