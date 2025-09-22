package org.whatsong.project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.whatsong.project.api.TokenStorage
import org.whatsong.project.api.createTokenStorage
import org.whatsong.project.api.repository.LobbyRepository
import org.whatsong.project.api.repository.ResultWrapper
import org.whatsong.project.models.CreateGameRoomRequest
import org.whatsong.project.models.Room
import org.whatsong.project.states.CreateRoomState
import org.whatsong.project.states.LobbyState

class LobbyViewModel (
    private val lobbyRepository: LobbyRepository,
    private val tokenStorage: TokenStorage = createTokenStorage()
): ViewModel() {
    private val _lobbyState = MutableStateFlow(LobbyState())
    val lobbyState: StateFlow<LobbyState> = _lobbyState.asStateFlow()

    private val _createRoomState = MutableStateFlow(CreateRoomState())
    val createRoomState: StateFlow<CreateRoomState> = _createRoomState.asStateFlow()


    fun refreshLobby(channelNo: Int) {
        _lobbyState.update { it.copy(refreshKey = it.refreshKey + 1) }
        loadRooms(channelNo)
    }

    fun fetchUsers(channelNo: Int) {
        val accessToken = tokenStorage.getAccessToken() ?: return
        viewModelScope.launch {
            when (val result = lobbyRepository.getChannelUser(accessToken,channelNo)) {
                is ResultWrapper.Success -> {
                    val sorted = result.data.ChannelUserResponseItems.sortedByDescending { it.level }
                    _lobbyState.update {
                        it.copy(users = sorted)
                    }
                }
                is ResultWrapper.Failure -> {
                    println("유저 목록 불러오기 실패: ${result.cause}")
                }
            }
        }
    }





    fun openCreateRoomModal() {
        _createRoomState.update { it.copy(isModalOpen = true) }
    }

    fun closeCreateRoomModal() {
        _createRoomState.update {
            it.copy(
                isModalOpen = false,
                roomName = "",
                password = "",

            )
        }
    }

    fun updateRoomName(name: String) {
        if (name.length <= 18) {
            _createRoomState.update {
                it.copy(
                    roomName = name,
                    errors = it.errors - "roomName"
                )
            }
        }
    }

    fun toggleMusicYear(year: String) {
        _createRoomState.update { state ->
            val newYears = if (state.musicYear.contains(year)) {
                state.musicYear - year
            } else {
                state.musicYear + year
            }
            state.copy(musicYear = newYears)
        }
    }

    // 방생성 후 입장
    fun createRoom(
        onNavigateToGame: (roomNo: Int, requestBody: Map<String, Any>) -> Unit
    ) {
        viewModelScope.launch {
            val createGameRoomRequest = CreateGameRoomRequest(
                channelNo = lobbyState.value.channelNo,
                roomName = createRoomState.value.roomName,
                password = createRoomState.value.password,
                musicYear = createRoomState.value.musicYear,
                maxUserNumber = createRoomState.value.maxUserNumber,
                quizAmount = createRoomState.value.quizAmount
            )

            when(val createResult = lobbyRepository.createRoom(createGameRoomRequest)) {
                is ResultWrapper.Success -> {
                    val gameRoomNo = createResult.data.gameRoomNo

                    when(val enterResult = lobbyRepository.enterRoom(gameRoomNo)) {
                        is ResultWrapper.Success -> {
                            val userInfoResponse = enterResult.data
                            val requestBody = mapOf(
                                "channelNo" to lobbyState.value.channelNo,
                                "roomName" to createRoomState.value.roomName,
                                "password" to createRoomState.value.password,
                                "musicYear" to createRoomState.value.musicYear,
                                "quizAmount" to createRoomState.value.quizAmount,
                                "data" to userInfoResponse,
                                "maxUserNumber" to createRoomState.value.maxUserNumber
                            )
                            onNavigateToGame(gameRoomNo, requestBody)
                        }
                        is ResultWrapper.Failure -> {
                            // TODO: 에러 상태를 State에 반영해서 UI에서 보여주도록 처리

                        }
                    }
                }
                is ResultWrapper.Failure -> {
                    // TODO: 에러 상태를 State에 반영해서 UI에서 보여주도록 처리

                }
            }
        }
    }

    // 공개방입장
    fun enterRoom(
        channelNo: Int,
        room: Room,
        onNavigateToGame: (roomNo: Int, requestBody: Map<String, Any>) -> Unit
    ) {
        viewModelScope.launch {
            when (val enterResult = lobbyRepository.enterRoom(room.gameRoomNo)) {
                is ResultWrapper.Success -> {
                    val userInfoResponse = enterResult.data
                    val requestBody = mapOf(
                        "channelNo" to channelNo.toInt(),
                        "roomName" to room.roomTitle,
                        "password" to "",
                        "musicYear" to room.years,
                        "quizAmount" to room.quizAmount,
                        "data" to userInfoResponse,
                        "maxUserNumber" to room.maxUserNumber
                    )
                    onNavigateToGame(room.gameRoomNo, requestBody)
                }
                is ResultWrapper.Failure -> {
                    // TODO: 에러 상태를 State에 반영해서 UI에서 보여주도록 처리

                }
            }
        }
    }

    fun loadRooms(channelNumber: Int) {
        viewModelScope.launch {
            _lobbyState.update { it.copy(isLoading = true) }

            when (val result = lobbyRepository.getRooms(channelNumber)) {
                is ResultWrapper.Success -> {
                    _lobbyState.update {
                        it.copy(
                            rooms = result.data.roomList,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is ResultWrapper.Failure -> {
                    _lobbyState.update {
                        it.copy(
                            isLoading = false,
                            error = "방 목록을 가져오는데 실패했습니다: ${result.cause.message}"
                        )
                    }

                }
            }
        }
    }

    // 비공개방 입장
    fun verifyAndEnterRoom(
        roomNo: Int,
        password: String,
        channelNo: Int,
        onNavigateToGame: (roomNo: Int, requestBody: Map<String, Any>) -> Unit
    ) {
        viewModelScope.launch {
            when (val verificationResult = lobbyRepository.verifyPassword(password, roomNo)) {
                is ResultWrapper.Success -> {
                    if (verificationResult.data.isCorrectPassword) {
                        // 방 입장
                        when (val enterResult = lobbyRepository.enterRoom(roomNo)) {
                            is ResultWrapper.Success -> {
                                val room = lobbyState.value.rooms.find { it.gameRoomNo == roomNo }!!
                                val requestBody = mapOf(
                                    "channelNo" to channelNo,
                                    "roomName" to room.roomTitle,
                                    "password" to password,
                                    "musicYear" to room.years,
                                    "quizAmount" to room.quizAmount,
                                    "data" to enterResult.data,
                                    "maxUserNumber" to room.maxUserNumber
                                )
                                onNavigateToGame(roomNo, requestBody)
                            }
                            is ResultWrapper.Failure -> {

                            }
                        }
                    } else {

                    }
                }
                is ResultWrapper.Failure -> {

                }
            }
        }
    }


}