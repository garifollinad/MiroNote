package com.example.mironote.model

import com.google.gson.annotations.SerializedName

data class StickyNoteResponse (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("links") val links : Link,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("createdBy") val createdBy : CreatedBy,
    @SerializedName("data") val data : Data,
    @SerializedName("geometry") val geometry : Geometry,
    @SerializedName("modifiedAt") val modifiedAt : String,
    @SerializedName("modifiedBy") val modifiedBy : CreatedBy,
    @SerializedName("position") val position : PositionResponse,
    @SerializedName("style") val style : Style
)

data class Link (
    @SerializedName("self") val self : String
)

data class CreatedBy (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String
)

data class Geometry (
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int
)

data class Style (
    @SerializedName("fillColor") val fillColor : String,
    @SerializedName("textAlign") val textAlign : String,
    @SerializedName("textAlignVertical") val textAlignVertical : String
)

data class PositionResponse (
    @SerializedName("origin") val origin : String,
    @SerializedName("x") val x : Double,
    @SerializedName("y") val y : Double,
    @SerializedName("relativeTo") val relativeTo : String
)