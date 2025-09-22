package org.whatsong.project.models

data class LoginResponseDto(
    val nickname: String,
    val accessToken: String,
    val refreshToken: String
)

data class guestLoginResponseDto(
    val nickname: String
)