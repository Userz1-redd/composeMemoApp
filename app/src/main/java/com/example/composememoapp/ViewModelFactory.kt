package com.example.composememoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composememoapp.data.source.MemoRepository
import com.example.composememoapp.list.ListViewModel

class ViewModelFactory(
    private val memoRepository: MemoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ListViewModel::class.java) ->
                    ListViewModel(memoRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}