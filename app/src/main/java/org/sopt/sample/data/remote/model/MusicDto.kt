package org.sopt.sample.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetMusicDto(
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Music>?
) {
    @Serializable
    data class Music(
        @SerialName("id")
        val id: Int,
        @SerialName("image")
        val image: String,
        @SerialName("title")
        val title: String,
        @SerialName("singer")
        val singer: String
    )
}

//@Serializable
//data class ResponseRegisterMusicDto(
//    @SerialName("statusCode") val statusCode: Int,
//    @SerialName("success") val success: Boolean,
//    @SerialName("message") val message: String,
//    @SerialName("data") val data: ResponseGetMusicDto.Music
//)