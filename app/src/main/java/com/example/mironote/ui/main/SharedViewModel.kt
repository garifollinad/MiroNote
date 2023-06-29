package com.example.mironote.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mironote.base.BaseViewModel
import com.example.mironote.model.StickyNote
import com.example.mironote.repository.BoardRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val boardRepository: BoardRepository
): BaseViewModel() {
    private val state = MutableLiveData<SharedResult>()
    val liveData: LiveData<SharedResult> = state

    fun getBoardItems(boardId: String){
        addDisposable(
            boardRepository.getBoardItems(boardId = boardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state.value = SharedResult.BoardItems(itemsList = result.data)
                    },
                    { error -> state.value = SharedResult.Error(error = error.message) }
                )
        )
    }

    sealed class SharedResult {
        data class BoardItems(val itemsList: List<StickyNote>) : SharedResult()
        data class Error(val error: String?) : SharedResult()

    }
}