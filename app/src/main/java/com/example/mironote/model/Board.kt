package com.example.mironote.model

import com.google.gson.annotations.SerializedName

data class BoardReponse (
    @SerializedName("size") val size : Int,
    @SerializedName("offset") val offset : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("total") val total : Int,
    @SerializedName("data") val data : List<Board>,
    @SerializedName("links") val links : Link,
    @SerializedName("type") val type : String
)

data class Board (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("links") val links : LinkBoard,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("createdBy") val createdBy : CreatedByBoard,
    @SerializedName("currentUserMembership") val currentUserMembership : CurrentUserMembership,
    @SerializedName("modifiedAt") val modifiedAt : String,
    @SerializedName("modifiedBy") val modifiedBy : CreatedByBoard,
    @SerializedName("owner") val owner : CreatedByBoard,
    @SerializedName("permissionsPolicy") val permissionsPolicy : PermissionsPolicy,
    @SerializedName("picture") val picture : Picture,
    @SerializedName("policy") val policy : Policy,
    @SerializedName("sharingPolicy") val sharingPolicy: SharingPolicy,
    @SerializedName("team") val team: CreatedByBoard,
    @SerializedName("viewLink") val viewLink: String
)

data class LinkBoard (
    @SerializedName("self") val self : String,
    @SerializedName("related") val related : String
)

data class CreatedByBoard (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("name") val name : String
)

data class CurrentUserMembership (
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("name") val name : String,
    @SerializedName("role") val role : String
)

data class PermissionsPolicy (
    @SerializedName("collaborationToolsStartAccess") val collaborationToolsStartAccess : String,
    @SerializedName("copyAccess") val copyAccess : String,
    @SerializedName("copyAccessLevel") val copyAccessLevel : String,
    @SerializedName("sharingAccess") val sharingAccess : String
)

data class Picture (
    @SerializedName("id") val id : Long,
    @SerializedName("type") val type : String,
    @SerializedName("imageURL") val imageURL : String
)

data class Policy (
    @SerializedName("permissionsPolicy") val permissionsPolicy : PermissionsPolicy,
    @SerializedName("sharingPolicy") val sharingPolicy : SharingPolicy
)

data class SharingPolicy (
    @SerializedName("access") val access : String,
    @SerializedName("inviteToAccountAndBoardLinkAccess") val inviteToAccountAndBoardLinkAccess : String,
    @SerializedName("organizationAccess") val organizationAccess : String,
    @SerializedName("teamAccess") val teamAccess : String,
)