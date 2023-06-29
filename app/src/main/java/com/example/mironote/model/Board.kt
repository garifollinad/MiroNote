package com.example.mironote.model

import com.google.gson.annotations.SerializedName

data class BoardReponse (
    @SerializedName("size") val size : Int,
    @SerializedName("data") val data : List<Board>
)

data class Board (
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("modifiedAt") val modifiedAt : String,
    @SerializedName("picture") val picture : Picture,
    @SerializedName("team") val team: CreatedByBoard
)

data class CreatedByBoard (
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String
)

data class Picture (
    @SerializedName("id") val id : Long,
    @SerializedName("type") val type : String,
    @SerializedName("imageURL") val imageURL : String
)
