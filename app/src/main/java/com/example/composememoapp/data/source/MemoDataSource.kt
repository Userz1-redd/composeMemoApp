package com.example.composememoapp.data.source

import com.example.composememoapp.data.Memo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface MemoDataSource {

    fun loadMemoList() : Flowable<List<Memo>>

    fun addMemo(memo : Memo) : Completable

    fun modifyMemo(memo : Memo) : Completable
}