package com.example.composememoapp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource
import com.example.composememoapp.data.source.MemoRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository : MemoRepository) : ViewModel() {


    fun loadMemoList(){
        viewModelScope.launch{
            repository.loadMemoList(object : MemoDataSource.LoadMemoListCallback{
                override fun onMemoListLoaded(list: List<Memo>) {

                }

                override fun onDataNotAvailable() {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun addMemo(memo : Memo){
        viewModelScope.launch{
            repository.addMemo(memo)
        }
    }
}