package com.example.composememoapp.list

import android.util.Log
import androidx.lifecycle.*
import com.example.composememoapp.data.Memo
import com.example.composememoapp.data.source.MemoDataSource
import com.example.composememoapp.data.source.MemoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(private val repository : MemoRepository) : ViewModel() {

    private val _memoItems = MutableLiveData(listOf<Memo>())
    val memoItems: LiveData<List<Memo>>
        get() = _memoItems

    val compositeDisposable = CompositeDisposable()
    init{
        repository.loadMemoList()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribe({
                _memoItems.postValue(it)
            })
            .addTo(compositeDisposable)
    }


    fun addMemo(memo : Memo){
        Log.d("TAG","Add Memo ${memo}")
        repository.addMemo(memo)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    fun modifyMemo(memo : Memo){
        repository.modifyMemo(memo)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared(){
        compositeDisposable.clear()
    }
}