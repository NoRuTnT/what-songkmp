package org.whatsong.project.api.repository

import org.whatsong.project.api.whatsongApis
import org.whatsong.project.models.LoginRequestDto
import org.whatsong.project.models.LoginResponseDto
import org.whatsong.project.models.guestLoginResponseDto

class AuthRepository(private val apiClient: whatsongApis) {
    suspend fun login(request: LoginRequestDto): ResultWrapper<LoginResponseDto> {
        return try {
            val response: LoginResponseDto =
                apiClient.post("/auth/login", body = request)
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

    suspend fun guestLogin(): ResultWrapper<guestLoginResponseDto> {
        return try {
            val response: guestLoginResponseDto = apiClient.post<guestLoginResponseDto>("/auth/guest")
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

}