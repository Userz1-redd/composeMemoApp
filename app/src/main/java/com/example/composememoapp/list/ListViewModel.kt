package com.example.composememoapp.list

import android.util.Log
import androidx.lifecycle.*
import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource
import com.example.composememoapp.data.source.MemoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(private val repository : MemoRepository) : ViewModel() {

    private var _memoItems = MutableStateFlow(listOf<Memo>())
    var memoItems: StateFlow<List<Memo>> = _memoItems
    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadMemoList().distinctUntilChanged()
                .collect{ list ->
                    _memoItems.value = list
                }
        }
    }


    fun addMemo(memo : Memo){
        Log.d("TAG","Add Memo ${memo}")
        viewModelScope.launch{
            repository.addMemo(memo)
        }
    }
}