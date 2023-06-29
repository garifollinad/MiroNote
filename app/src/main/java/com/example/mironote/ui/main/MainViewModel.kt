package com.example.mironote.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mironote.base.BaseViewModel
import com.example.mironote.model.Board
import com.example.mironote.model.BoardReponse
import com.example.mironote.model.StickyNote
import com.example.mironote.model.StickyNoteBody
import com.example.mironote.repository.BoardRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : BaseViewModel() {

    private val state = MutableLiveData<Result>()
    val liveData: LiveData<Result> = state

    fun addStickyNote(boardId: String, noteText: String, noteColor: String){
        addDisposable(
            boardRepository.addStickyNote(boardId = boardId, noteText = noteText, noteColor = noteColor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state.value = Result.StickerSuccess(success = true)
                    },
                    { error -> state.value = Result.StickerError(error = error.message) }
                )
        )
    }

    fun updateStickyNode(boardId: String, itemId: String, noteText: String, noteColor: String) {
        addDisposable(
            boardRepository.updateStickyNote(boardId = boardId, itemId = itemId, noteText = noteText, noteColor = noteColor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state.value = Result.StickerSuccess(success = true)
                    },
                    { error -> state.value = Result.StickerError(error = error.message) }
                )
        )
    }

    fun deleteStickyNode(boardId: String, itemId: String) {
        addDisposable(
            boardRepository.deleteStickyNote(boardId = boardId, itemId = itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state.value = Result.StickerDelete(success = true)
                    },
                    { error -> state.value = Result.StickerError(error = error.message) }
                )
        )
    }

    fun getBoards(){
        addDisposable(
            boardRepository.getBoards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                         state.value = Result.Boards(boardsList = result.data)
                    },
                    { error -> state.value = Result.Error(error = error.message) }
                )
        )
    }

    fun getBoardItems(boardId: String){
        addDisposable(
            boardRepository.getBoardItems(boardId = boardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state.value = Result.BoardItems(itemsList = result.data)
                    },
                    { error -> state.value = Result.Error(error = error.message) }
                )
        )
    }

    sealed class Result {
        data class StickerError(val error: String?) : Result()
        data class StickerDelete(val success: Boolean?) : Result()
        data class StickerSuccess(val success: Boolean?) : Result()
        data class Boards(val boardsList: List<Board>) : Result()

        data class BoardItems(val itemsList: List<StickyNote>) : Result()
        data class Error(val error: String?) : Result()
    }
}