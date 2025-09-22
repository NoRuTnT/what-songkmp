package org.whatsong.project.api.repository

import io.ktor.client.request.*
import org.whatsong.project.api.whatsongApis
import org.whatsong.project.models.RoomList
import org.whatsong.project.models.UserData

class UserRepository(private val apiClient: whatsongApis) {
    suspend fun getUserInfo(accessToken: String): ResultWrapper<UserData> {
        return try {
            val response: UserData = apiClient.get("/member/myinfo"){
                header("accessToken", accessToken)
            }
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}