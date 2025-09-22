package org.whatsong.project.component.LobbyRooms

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLInputElement
import org.whatsong.project.models.Room
import org.whatsong.project.viewmodels.LobbyViewModel


@Composable
fun PasswordModal(
    channelNo: Int,
    onClose: () -> Unit,
    selectedRoomNumber: Int?,
    viewModel: LobbyViewModel,
    onNavigateToGame: (gameRoomNo: Int, requestBody: Map<String, Any>) -> Unit
) {
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val handleSubmit = {
        selectedRoomNumber?.let { roomNo ->
            viewModel.verifyAndEnterRoom(roomNo, password, channelNo,onNavigateToGame)
        }
    }

    // 모달 오버레이
    Div(
        attrs = {
            classes(LobbyRoomsStyles.modalOverlay)
        }
    ) {
        Div(
            attrs = {
                classes(LobbyRoomsStyles.modalContainer)
            }
        ) {
            // 로고
            Img(
                src = "/assets/svgs/logo.svg",
                attrs = {
                    classes(LobbyRoomsStyles.modalLogo)
                }
            )

            // 비밀번호 입력
            Input(
                type = InputType.Password,
                attrs = {
                    placeholder("방 비밀번호 입력")
                    maxLength(4)
                    attr("autocomplete", "off")
                    value(password)
                    onInput { event ->
                        password = (event.target as HTMLInputElement).value
                    }
                    onKeyDown { event ->
                        if (event.key == "Enter") {
                            handleSubmit()
                        }
                    }
                    classes(LobbyRoomsStyles.modalPasswordInput)
                }
            )

            Div(
                attrs = {
                    classes(LobbyRoomsStyles.modalButtonContainer)
                }
            ) {
                // 확인 버튼
                Button(
                    attrs = {
                        onClick { handleSubmit() }
                        classes(LobbyRoomsStyles.modalConfirmButton)
                    }
                ) {
                    Text("확인")
                }

                // 닫기 버튼
                Button(
                    attrs = {
                        onClick { onClose() }
                        classes(LobbyRoomsStyles.modalCancelButton)
                    }
                ) {
                    Text("취소")
                }
            }
        }
    }
}

@Composable
fun LobbyRooms(
    channelNo: Int,
    viewModel: LobbyViewModel,
    onNavigateToGame: (gameRoomNo: Int, requestBody: Map<String, Any>) -> Unit
) {
    val lobbyState by viewModel.lobbyState.collectAsState()
    var currentPage by remember { mutableStateOf(1) }
    var isModalOpen by remember { mutableStateOf(false) }
    var selectedRoomNumber by remember { mutableStateOf<Int?>(null) }
    val pageSize = 6

    // 방 목록 가져오기
    LaunchedEffect(channelNo) {
        viewModel.loadRooms(channelNo)
    }

    // 페이지네이션 계산
    val indexOfFirstRoom = (currentPage - 1) * pageSize
    val indexOfLastRoom = minOf(currentPage * pageSize, lobbyState.rooms.size)
    val currentRooms = lobbyState.rooms.subList(indexOfFirstRoom, indexOfLastRoom)
    val totalPages = (lobbyState.rooms.size + pageSize - 1) / pageSize

    // 연도 범위 계산
    fun getYearsRange(years: List<Int>): String {
        return if (years.size == 1) {
            "${years[0]}년"
        } else {
            val minYear = years.minOrNull() ?: 0
            val maxYear = years.maxOrNull() ?: 0
            "${minYear}년 ~ ${maxYear}년"
        }
    }

    // 방 클릭 핸들러
    val handleRoomClick = { room: Room ->
        if (room.isPrivate) {
            // 비밀방 모달 처리
            selectedRoomNumber = room.gameRoomNo
            isModalOpen = true
        } else {
            viewModel.enterRoom(channelNo, room, onNavigateToGame)
        }
    }

    // 로딩 상태 표시
    if (lobbyState.isLoading) {
        Div(
            attrs = {
                classes(LobbyRoomsStyles.loadingContainer)
            }
        ) {
            Text("방 목록을 불러오는 중...")
        }
        return
    }

    // 에러 상태 표시
    if (lobbyState.error != null) {
        Div(
            attrs = {
                classes(LobbyRoomsStyles.errorContainer)
            }
        ) {
            Text(lobbyState.error!!)
        }
        return
    }

    if (lobbyState.rooms.isEmpty()) {
        // 방이 없을 때
        Div(
            attrs = {
                classes(LobbyRoomsStyles.emptyRoomContainer)
            }
        ) {
            Img(
                src = "/assets/svgs/MultiLobby/noRoomIcon.svg",
                attrs = {
                    classes(LobbyRoomsStyles.emptyRoomIcon)
                }
            )
            Text("생성된 방이 없습니다")
        }
    } else {
        Div(
            attrs = {
                classes(LobbyRoomsStyles.lobbyContainer)
            }
        ) {
            // 방 목록 그리드
            Div(
                attrs = {
                    classes(LobbyRoomsStyles.roomGrid)
                }
            ) {
                currentRooms.forEach { room ->
                    Div(
                        attrs = {
                            onClick {
                                if (!room.isPlay) handleRoomClick(room)
                            }
                            // 조건에 따라 다른 스타일 클래스 적용
                            classes(
                                if (room.isPlay)
                                    LobbyRoomsStyles.roomCardInactive
                                else
                                    LobbyRoomsStyles.roomCardActive
                            )
                        }
                    ) {
                        // 방 번호
                        Div(
                            attrs = {
                                classes(LobbyRoomsStyles.roomNumber)
                            }
                        ) {
                            Text("방 #${room.gameRoomNo}")
                        }

                        // 방 제목
                        Div(
                            attrs = {
                                classes(LobbyRoomsStyles.roomTitle)
                            }
                        ) {
                            Text(room.roomTitle)
                        }

                        // 방장
                        Div(
                            attrs = {
                                classes(LobbyRoomsStyles.roomInfo)
                            }
                        ) {
                            Text("${room.roomManager}님의 방")
                        }

                        // 연도 범위
                        Div(
                            attrs = {
                                classes(LobbyRoomsStyles.roomInfo)
                            }
                        ) {
                            Text(getYearsRange(room.years))
                        }

                        Div(
                            attrs = {
                                classes(LobbyRoomsStyles.roomBottomInfo)
                            }
                        ) {
                            // 인원수
                            Div(
                                attrs = {
                                    classes(
                                        if (room.currentMembers == room.maxUserNumber)
                                            LobbyRoomsStyles.memberCountFull
                                        else
                                            LobbyRoomsStyles.memberCountNormal
                                    )
                                }
                            ) {
                                Text("${room.currentMembers}/${room.maxUserNumber}")
                            }

                            // 잠금 아이콘
                            Img(
                                src = if (room.isPrivate)
                                    "/assets/svgs/MultiLobby/roomLock.svg"
                                else
                                    "/assets/svgs/MultiLobby/roomUnlock.svg",
                                attrs = {
                                    classes(LobbyRoomsStyles.lockIcon)
                                }
                            )

                            // 문제 수
                            Div(
                                attrs = {
                                    classes(LobbyRoomsStyles.quizCount)
                                }
                            ) {
                                Text("${room.quizAmount}문제")
                            }
                        }
                    }
                }
            }

            // 페이지네이션 버튼들
            Div(
                attrs = {
                    classes(LobbyRoomsStyles.paginationContainer)
                }
            ) {
                Row {  }
                // 이전 버튼
                Button(
                    attrs = {
                        onClick {
                            if (currentPage > 1) currentPage--
                        }
                        if (currentPage == 1) {
                            disabled()
                        }
                        classes(
                            if (currentPage == 1)
                                LobbyRoomsStyles.paginationButtonDisabled
                            else
                                LobbyRoomsStyles.paginationButtonActive
                        )
                    }
                ) {
                    Img(
                        src = "/assets/svgs/modeSelectSvgs/nextButton.svg",
                        attrs = {
                            classes(LobbyRoomsStyles.paginationPrevIcon)
                        }
                    )
                }

                // 페이지 정보
                Div(
                    attrs = {
                        classes(LobbyRoomsStyles.pageInfo)
                    }
                ) {
                    Text("$currentPage / $totalPages")
                }

                // 다음 버튼
                Button(
                    attrs = {
                        onClick {
                            if (currentPage < totalPages) currentPage++
                        }
                        if (currentPage == totalPages) {
                            disabled()
                        }
                        classes(
                            if (currentPage == totalPages)
                                LobbyRoomsStyles.paginationButtonDisabled
                            else
                                LobbyRoomsStyles.paginationButtonActive
                        )
                    }
                ) {
                    Img(
                        src = "/assets/svgs/modeSelectSvgs/nextButton.svg",
                        attrs = {
                            classes(LobbyRoomsStyles.paginationIcon)
                        }
                    )
                }
            }
        }
    }

    // 비밀번호 모달
    if (isModalOpen && selectedRoomNumber != null) {
        PasswordModal(
            channelNo = channelNo,
            selectedRoomNumber = selectedRoomNumber,
            viewModel = viewModel,
            onNavigateToGame = { gameRoomNo, requestBody ->
                isModalOpen = false
                selectedRoomNumber = null
            },
            onClose = {
                isModalOpen = false
                selectedRoomNumber = null
            }
        )
    }
}
