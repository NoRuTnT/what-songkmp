package org.whatsong.project.api.repository


import io.ktor.client.request.*
import org.whatsong.project.api.whatsongApis
import org.whatsong.project.models.*



sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure(val cause: Throwable) : ResultWrapper<Nothing>()
}

class LobbyRepository(private val apiClient: whatsongApis) {
    suspend fun getRooms(channelNo: Int): ResultWrapper<RoomList> {
        return try {
            val response:RoomList = apiClient.get("/game/main/$channelNo")
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

    suspend fun verifyPassword(
        password: String,
        gameRoomNo: Int
    ): ResultWrapper<PasswordVerificationResponse> {
        return try {
            val body = mapOf(
                "password" to password,
                "gameRoomNo" to gameRoomNo
            )
            val response: PasswordVerificationResponse =
                apiClient.post("/game/main/password", body)
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

    suspend fun enterRoom(
        gameRoomNo: Int
    ): ResultWrapper<EnterGameRoomResponse> {
        return try {
            val response: EnterGameRoomResponse =
                apiClient.get("/game/main/enter/$gameRoomNo")
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

    suspend fun createRoom(
        createGameRoomRequest: CreateGameRoomRequest
    ): ResultWrapper<CreateGameRoomResponse>{
        return try {
            val body = CreateGameRoomRequest(
                channelNo = createGameRoomRequest.channelNo,
                roomName = createGameRoomRequest.roomName,
                password = createGameRoomRequest.password,
                musicYear = createGameRoomRequest.musicYear,
                maxUserNumber = createGameRoomRequest.maxUserNumber,
                quizAmount = createGameRoomRequest.quizAmount
            )

            val response: CreateGameRoomResponse =
                apiClient.post("/game/main/create",body)
            ResultWrapper.Success(response)
        } catch (e: Exception){
            ResultWrapper.Failure(e)
        }
    }

    suspend fun getChannelUser(
        accessToken: String,
        channelNo: Int
    ): ResultWrapper<ChannelUserResponse>{
        return try{
            val response: ChannelUserResponse =
                apiClient.get("/game/$channelNo"){
                    header("accessToken", accessToken)
                }

            ResultWrapper.Success(response)
        } catch (e: Exception){
            ResultWrapper.Failure(e)
        }

    }
}