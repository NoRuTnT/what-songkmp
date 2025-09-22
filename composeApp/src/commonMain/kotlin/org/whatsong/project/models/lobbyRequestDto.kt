package org.whatsong.project.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRoomRequest(
    val channelNo: Int,
    val roomName: String,
    val password: String,
    val musicYear: List<String>,
    val maxUserNumber: Int,
    val quizAmount: Int
)