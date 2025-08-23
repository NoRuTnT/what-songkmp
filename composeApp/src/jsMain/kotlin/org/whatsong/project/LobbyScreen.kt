package org.whatsong.project

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*

@Composable
fun LobbyScreen() {
    Style(LobbyStyles)

    Div({ classes(LobbyStyles.page) }) {

        // 상단 헤더
        Div({ classes(LobbyStyles.header) }) {
            Button({ classes(LobbyStyles.button) }) { Text("로그인하기") }
            Button({ classes(LobbyStyles.button) }) { Text("새로고침") }
            Button({ classes(LobbyStyles.button, LobbyStyles.greenButton) }) { Text("방 만들기") }
        }

        // 메인 컨테이너
        Div({ classes(LobbyStyles.container) }) {

            // 좌측 사이드바
            Div({ classes(LobbyStyles.sidebar) }) {
                H3 { Text("1차 채널") }
                Div({ classes(LobbyStyles.userList) }) {
                    repeat(5) {
                        Div({ classes(LobbyStyles.userItem) }) {
                            Span { Text("유저${it+1}") }
                            Span { Text("Lv.${it+1}") }
                        }
                    }
                }

                Div({ classes(LobbyStyles.profileBox) }) {
                    H4 { Text("내 정보") }
                    P { Text("닉네임: 차라") }
                    P { Text("레벨: 7") }
                    P { Text("승리 횟수: 23") }
                }
            }

            // 중앙 게임방 목록
            Div({ classes(LobbyStyles.mainArea) }) {
                repeat(4) { i ->
                    Div({ classes(LobbyStyles.roomCard) }) {
                        Div({ classes(LobbyStyles.roomHeader) }) {
                            H4 { Text("10${i+1} 방 이름") }
                            Span({ classes(LobbyStyles.badge) }) { Text("게임중") }
                        }
                        P { Text("문제 수: ${10 + i * 5}문제") }
                        P { Text("기간: 1990년~2015년") }
                        Button({ classes(LobbyStyles.button) }) { Text("입장") }
                    }
                }
            }
        }
    }
}