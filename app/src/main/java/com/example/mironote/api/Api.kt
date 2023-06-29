package com.example.mironote.api

import com.example.mironote.model.BoardReponse
import com.example.mironote.model.ItemResponse
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

    @GET("boards/{board_id}/items")
    fun getBoardItems(
        @Header("Authorization") token: String, @Path("board_id") boardId: String)
            : Single<Response<ItemResponse>>

    @POST("boards/{board_id}/sticky_notes")
    fun addStickyNote(
        @Header("Authorization") token: String, @Path("board_id") boardId: String, @Body body: StickyNoteBody
    ): Single<Response<StickyNoteResponse>>

    @PATCH("boards/{board_id}/sticky_notes/{item_id}")
    fun updateStickyNote(
        @Header("Authorization") token: String, @Path("board_id") boardId: String, @Path("item_id") itemId: String, @Body body: StickyNoteBody
    ): Single<Response<StickyNoteResponse>>

    @DELETE("boards/{board_id}/sticky_notes/{item_id}")
    fun deleteStickyNote(
        @Header("Authorization") token: String, @Path("board_id") boardId: String, @Path("item_id") itemId: String)
            : Single<Response<ItemResponse>>
}