package org.whatsong.project


import androidx.compose.runtime.*

@Composable
fun App() {
    var currentPage by remember { mutableStateOf("login") }
    val loginState = remember { LoginState() }

    when (currentPage) {
        "login" -> LoginScreenWeb(
            loginState = loginState,
            onLoginSuccess = { currentPage = "lobby" }
        )
        "lobby" -> LobbyScreen()
    }
}