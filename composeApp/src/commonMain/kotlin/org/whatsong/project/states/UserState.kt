package org.whatsong.project.states

data class UserState(
    val nickname: String? = null,
    val level: Long = 0,
    val exp: Double = 0.0,
    val wins: Long = 0
)