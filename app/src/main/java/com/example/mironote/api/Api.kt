package com.example.mironote.api

import com.example.mironote.model.BoardReponse
import com.example.mironote.model.StickyNoteResponse
import com.example.mironote.model.StickyNoteBody
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("boards")
    fun getBoards(
        @Header("Authorization") token: String)
    : Single<Response<BoardReponse>>

    @POST("boards/{board_id}/sticky_notes")
    fun addStickyNote(
        @Header("Authorization") token: String, @Path("board_id") boardId: String, @Body body: StickyNoteBody
    ): Single<Response<StickyNoteResponse>>
}