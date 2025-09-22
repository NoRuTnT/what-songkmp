package org.whatsong.project.component.LobbyChatting
import androidx.compose.runtime.*
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.whatsong.project.models.ChatMessage
import org.whatsong.project.network.StompClient


@Composable
fun LobbyChatting(
    socketClient: StompClient,
    lobbyChatList: List<ChatMessage>,
    channelNo: Int,
    myNickname: String?
) {
    Style(LobbyChattingStyles)

    var lobbyInputMessage by remember { mutableStateOf("") }
    var chatCount by remember { mutableStateOf(0) }
    var chatDisabled by remember { mutableStateOf(false) }
    var userScrolled by remember { mutableStateOf(false) }

    var chatContainer by remember { mutableStateOf<HTMLElement?>(null) }
    var chatEnd by remember { mutableStateOf<HTMLElement?>(null) }

    // 스크롤 이벤트 처리
    DisposableEffect(chatContainer) {
        val element = chatContainer
        val listener: (Event) -> Unit = {
            element?.let {
                userScrolled = it.scrollTop + it.clientHeight < it.scrollHeight - 20
            }
        }
        element?.addEventListener("scroll", listener)
        onDispose {
            element?.removeEventListener("scroll", listener)
        }
    }

    // 새로운 메시지가 추가되면 자동 스크롤
    LaunchedEffect(lobbyChatList.size) {
        if (!userScrolled) {
            chatEnd?.scrollIntoView(js("{ behavior: 'smooth' }"))
        }
    }

    fun sendMessage() {
        if (lobbyInputMessage.isBlank() || chatDisabled) return

        val headers = js("{}")
        val accessToken = window.localStorage.getItem("UAT")
        if (!accessToken.isNullOrBlank()) {
            headers.accessToken = accessToken
        }

        val msg = js("{}")
        msg.message = lobbyInputMessage
        msg.nickname = myNickname

        socketClient.send(
            destination = "/chat-message/$channelNo",
            body = JSON.stringify(msg),
            headers = headers
        )

        lobbyInputMessage = ""
        chatCount++

        if (chatCount >= 4) {
            chatDisabled = true
            window.setTimeout({
                chatDisabled = false
                chatCount = 0
            }, 3000)
        }
    }

    Div({ classes(LobbyChattingStyles.wrapper) }) {
        // 채팅 리스트
        Div({
            classes(LobbyChattingStyles.contentsWrapper)
            ref { element ->
                chatContainer = element?.unsafeCast<HTMLElement>()
                onDispose { }
            }
        }) {
            lobbyChatList.forEach { msg ->
                Div({ classes(LobbyChattingStyles.message) }) {
                    B { Text("${msg.nickname}: ") }
                    Text(msg.message)
                }
            }
            Div({ ref { element ->
                chatEnd = element?.unsafeCast<HTMLElement>()
                onDispose { }
            } })
        }

        // 입력창
        Div({ classes(LobbyChattingStyles.inputWrapper) }) {
            Input(type = InputType.Text) {
                classes(LobbyChattingStyles.input)
                placeholder(if (chatDisabled) "잠시 후 다시 시도해주세요" else "메시지를 입력하세요...")
                value(lobbyInputMessage)
                onInput {
                    if (it.value.length <= 100 && !chatDisabled) {
                        lobbyInputMessage = it.value
                    }
                }
                onKeyUp {
                    if (it.key == "Enter" && !chatDisabled && lobbyInputMessage.isNotBlank()) {
                        sendMessage()
                    }
                }
            }
            Button(attrs = {
                onClick { sendMessage() }
            }) {
//                Img(src = Res.drawable.chat_submit) {
//                    attr("alt", "메세지 보내기")
//                    style { width(27.px) }
//                }
            }
        }
    }
}