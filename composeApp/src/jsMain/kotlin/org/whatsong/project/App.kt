package org.whatsong.project


import androidx.compose.runtime.*
import org.whatsong.project.api.TokenStorage
import org.whatsong.project.api.createTokenStorage
import org.whatsong.project.navigation.Screen
import org.whatsong.project.page.LobbyScreen
import org.whatsong.project.page.LoginScreen
import org.whatsong.project.viewmodels.LobbyViewModel
import org.whatsong.project.viewmodels.LoginViewModel
import org.whatsong.project.viewmodels.UserViewModel

@Composable
fun App(
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    lobbyViewModel: LobbyViewModel,
    tokenStorage: TokenStorage = createTokenStorage()

) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (val screen = currentScreen) {
        // 로그인 화면
        Screen.Login -> {
            val state by loginViewModel.loginState.collectAsState()
            if (state.isLoggedIn) {
                val token = tokenStorage.getAccessToken() ?: ""
                userViewModel.loadUserInfo(token)
                currentScreen = Screen.Lobby
            }

            LoginScreen(
                loginViewModel = loginViewModel
            )
        }

        // 로비 화면
        Screen.Lobby -> LobbyScreen(
            lobbyViewModel = lobbyViewModel,
            userViewModel = userViewModel,
            onNavigateToGame = { roomNo, body ->
                currentScreen = Screen.Lobby //todo 게임방화면만들면 바꾸기
            }
        )

//        is Screen.Game -> GameScreen(
//            roomNo = screen.roomNo,
//            onNavigateBack = { currentScreen = Screen.Lobby }
//        )
    }
}