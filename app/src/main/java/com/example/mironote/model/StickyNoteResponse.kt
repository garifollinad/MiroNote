package com.example.mironote.model

import com.google.gson.annotations.SerializedName

data class StickyNoteResponse (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("data") val data : Data,
    @SerializedName("modifiedAt") val modifiedAt : String
)


