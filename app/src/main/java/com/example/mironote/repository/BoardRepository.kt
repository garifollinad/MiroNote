package com.example.mironote.repository

import com.example.mironote.api.Api
import com.example.mironote.model.*
import com.example.mironote.utils.PrefUtils
import io.reactivex.Single
import javax.inject.Inject

interface BoardRepository {
    fun addStickyNote(boardId: String, noteText: String, noteColor: String): Single<StickyNoteResponse>

    fun updateStickyNote(boardId: String, itemId: String, noteText: String, noteColor: String): Single<StickyNoteResponse>

    fun getBoards(): Single<BoardReponse>
    fun getBoardItems(boardId: String): Single<ItemResponse>
    fun deleteStickyNote(boardId: String, itemId: String): Single<String>
}

class BoardRepositoryImpl @Inject constructor(
    private val api: Api,
    private val prefUtils: PrefUtils
): BoardRepository {

    override fun addStickyNote(boardId: String, noteText: String, noteColor: String): Single<StickyNoteResponse> {
        return Single.fromCallable {
            StickyNoteBody(
                data = Data("square", noteText),
                style = Stylee(noteColor),
                position = Position("center", 0.0, 0.0)
            )
        }.flatMap { body ->
            api.addStickyNote(
                boardId = boardId,
                token = "Bearer " + prefUtils.getDataString(PrefUtils.TOKEN),
                body = body
            ).flatMap { response ->
                if (response.isSuccessful) {
                    val list = response.body()
                    Single.just(list)
                } else {
                    Single.error(Throwable(""))
                }
            }
        }
    }

    override fun updateStickyNote(boardId: String, itemId: String, noteText: String, noteColor: String): Single<StickyNoteResponse> {
        return Single.fromCallable {
            StickyNoteBody(
                data = Data("square", noteText),
                style = Stylee(noteColor),
                position = Position("center", 0.0, 0.0)
            )
        }.flatMap { body ->
            api.updateStickyNote(
                boardId = boardId,
                itemId = itemId,
                token = "Bearer " + prefUtils.getDataString(PrefUtils.TOKEN),
                body = body
            ).flatMap { response ->
                if (response.isSuccessful) {
                    val list = response.body()
                    Single.just(list)
                } else {
                    Single.error(Throwable(""))
                }
            }
        }
    }

    override fun deleteStickyNote(boardId: String, itemId: String): Single<String> {
        return api.deleteStickyNote(
            boardId = boardId,
            itemId = itemId,
            token = "Bearer " + prefUtils.getDataString(PrefUtils.TOKEN)
        ).flatMap { response ->
            if (response.isSuccessful) {
                Single.just("")
            } else {
                Single.error(Throwable(""))
            }
        }
    }

    override fun getBoards(): Single<BoardReponse> {
        return api.getBoards(
                token = "Bearer " + prefUtils.getDataString(PrefUtils.TOKEN)
            ).flatMap { response ->
                if (response.isSuccessful) {
                    val list = response.body()
                    Single.just(list)
                } else {
                    Single.error(Throwable(""))
                }
            }
        }

    override fun getBoardItems(boardId: String): Single<ItemResponse> {
        return api.getBoardItems(
            token = "Bearer " + prefUtils.getDataString(PrefUtils.TOKEN), boardId = boardId
        ).flatMap { response ->
            if (response.isSuccessful) {
                val list = response.body()
                Single.just(list)
            } else {
                Single.error(Throwable(""))
            }
        }
    }
}