package org.whatsong.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val roomTitle: String,
    val roomManager: String,
    val currentMembers: Int,
    val gameRoomNo: Int,
    val isPrivate: Boolean,
    val years: List<Int>,
    val quizAmount: Int,
    val isPlay: Boolean,
    val maxUserNumber: Int
)

@Serializable
data class RoomList(
    val roomList: List<Room>
)


@Serializable
data class UserInfoItem(
    val nickname: String,
    val score: Double = 0.0,
    val isSkipped: Boolean = false
)

@Serializable
data class ChannelUsersList(
    val nickname: String,
    val level: Long,
    val isGaming: Boolean
)

@Serializable
data class ChatMessage(
    val nickname: String,
    val message: String
)
