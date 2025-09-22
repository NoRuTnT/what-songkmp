package org.whatsong.project.models

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val nickname: String,
    val exp: Double,
    val level: Long,
    val wins: Long

)