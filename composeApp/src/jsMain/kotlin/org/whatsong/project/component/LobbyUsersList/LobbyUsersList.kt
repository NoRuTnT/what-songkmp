package org.whatsong.project.component.LobbyUsersList

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.whatsong.project.models.ChannelUsersList

@Composable
fun LobbyUsersList(users: List<ChannelUsersList>) {
    Style(LobbyUsersListStyles)
    Div({ classes(LobbyUsersListStyles.wrapper) }) {
        P({ classes(LobbyUsersListStyles.title) }) {
            Text("접속중인 유저")
        }
        Div({ classes(LobbyUsersListStyles.listWrapper) }) {
            users.forEach { user ->
                Div({ classes(LobbyUsersListStyles.userCell) }) {
                    Text("${user.nickname} ${user.level}Lv")
                }
            }
        }
    }
}