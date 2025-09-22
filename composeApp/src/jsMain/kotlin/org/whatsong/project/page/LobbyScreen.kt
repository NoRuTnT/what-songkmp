package org.whatsong.project.page

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.whatsong.project.component.LobbyChatting.LobbyChatting
import org.whatsong.project.component.LobbyRooms.LobbyRooms
import org.whatsong.project.component.LobbyUsersList.LobbyUsersList
import org.whatsong.project.component.RefreshButton.RefreshButton
import org.whatsong.project.models.ChatMessage
import org.whatsong.project.network.StompClient
import org.whatsong.project.viewmodels.LobbyViewModel
import org.whatsong.project.viewmodels.UserViewModel

@Composable
fun LobbyScreen(
    lobbyViewModel: LobbyViewModel,
    userViewModel: UserViewModel,
    onNavigateToGame: (roomNo: Int, body: Map<String, Any>) -> Unit
) {
    Style(LobbyStyles)

    val lobbyState by lobbyViewModel.lobbyState.collectAsState()
    val createRoomState by lobbyViewModel.createRoomState.collectAsState()
    val userState by userViewModel.userState.collectAsState()

    var chatMessages by remember(lobbyState.channelNo) { mutableStateOf<List<ChatMessage>>(emptyList()) }
    val stompClient = remember(lobbyState.channelNo) { StompClient() }

    // 페이지 마운트 언마운트 시 연결 관리
    DisposableEffect(Unit) {
        val brokerUrl = "wss://TEST_SOCKET_URL"

        val accessToken = kotlinx.browser.window.localStorage.getItem("UAT") ?: ""
        val channelNo = lobbyState.channelNo.toString()

        stompClient.connect(
            brokerUrl = brokerUrl,
            accessToken = accessToken,
            channelNo = channelNo,
            connectType = "ENTER_LOBBY"
        )

        stompClient.subscribe("/topic/$channelNo") { body ->
            val data = JSON.parse<dynamic>(body)
            chatMessages = chatMessages + ChatMessage(
                nickname = data.nickname as String,
                message = data.message as String
            )
        }

        onDispose {
            stompClient.disconnect()

        }
    }

    Div(
        attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                height(100.vh)
                padding(16.px)
            }
        }
    ) {
        // 상단 버튼들
        Div(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    justifyContent(JustifyContent.SpaceBetween)
                    marginBottom(16.px)
                }
            }
        ) {
            RefreshButton(onClick = { lobbyViewModel.refreshLobby(channelNo = lobbyState.channelNo) })
        }

        // 사용자 목록
        LobbyUsersList(users = lobbyState.users)

        // 방 목록
        LobbyRooms(channelNo = lobbyState.channelNo,
            viewModel = lobbyViewModel,
            onNavigateToGame = { roomNo, body -> onNavigateToGame(roomNo, body) })

        // 버튼들
        Div(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    justifyContent(JustifyContent.SpaceEvenly)
                    gap(10.px)
                    margin(20.px, 0.px)
                }
            }
        ) {
//            LobbyCreateRoomButton(
//                onOpenModal = { lobbyViewModel.openCreateRoomModal() }
//            )

        }

        // 채팅 (맨 아래)
        LobbyChatting(stompClient,lobbyChatList = lobbyState.chatMessages,lobbyState.channelNo,userState.nickname)
    }
}