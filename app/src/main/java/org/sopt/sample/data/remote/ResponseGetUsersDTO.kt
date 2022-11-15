package org.sopt.sample.data.remote

import android.net.Uri
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.net.URI

@Serializable
data class ResponseGetUsersDTO(
    @SerialName("page")
    val page : Int,
    @SerialName("per_page")
    val perPage : Int,
    @SerialName("total")
    val total : Int,
    @SerialName("total_pages")
    val totalPages : Int,
    @SerialName("data")
    val data : List<User>,
    @SerialName("support")
    val support : Support
){
    @Serializable
    data class User(
        @SerialName("id")
        val id : Int,
        @SerialName("email")
        val email : String,
        @SerialName("first_name")
        val firstName : String,
        @SerialName("last_name")
        val lastName : String,
        @SerialName("avatar")
        val avatar : String
    )
    @Serializable
    data class Support(
        @SerialName("url")
        val url : String,
        @SerialName("text")
        val text : String
    )
}