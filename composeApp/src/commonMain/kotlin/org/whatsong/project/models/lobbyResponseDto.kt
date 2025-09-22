package org.whatsong.project.models

import kotlinx.serialization.Serializable


@Serializable
data class CreateGameRoomResponse(
    val gameRoomNo: Int,
    val createGameRoomLogId: Int
)


@Serializable
data class PasswordVerificationResponse(
    val isCorrectPassword: Boolean
)


@Serializable
data class EnterGameRoomResponse(
    val userInfoItems: List<UserInfoItem>,
    val gameRoomManagerNickname: String,
    val enteredUserNickname: String
)

@Serializable
data class ChannelUserResponse(
    val ChannelUserResponseItems: List<ChannelUsersList>
)
