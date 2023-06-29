package com.example.mironote.model

import com.google.gson.annotations.SerializedName

data class StickyNote (
    @SerializedName("id") val id : String,
    @SerializedName("data") val data : Data,
    @SerializedName("style") val style : Stylee,
    @SerializedName("position") val position : Position,
)

data class StickyNoteBody (
    @SerializedName("data") val data : Data,
    @SerializedName("style") val style : Stylee,
    @SerializedName("position") val position : Position,
)

data class ItemResponse (
    @SerializedName("size") val size : Int,
    @SerializedName("data") val data : List<StickyNote>
)

data class Stylee (
    @SerializedName("fillColor") val fillColor : String
)

data class Data (
    @SerializedName("shape") val shape : String,
    @SerializedName("content") val content : String
)

data class Position (
    @SerializedName("origin") val origin : String,
    @SerializedName("x") val x : Double,
    @SerializedName("y") val y : Double
)