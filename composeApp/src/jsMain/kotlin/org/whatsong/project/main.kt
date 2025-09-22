package org.whatsong.project


import org.jetbrains.compose.web.renderComposable
import org.whatsong.project.api.createTokenStorage
import org.whatsong.project.api.repository.AuthRepository
import org.whatsong.project.api.repository.LobbyRepository
import org.whatsong.project.api.repository.UserRepository
import org.whatsong.project.api.whatsongApis
import org.whatsong.project.viewmodels.LobbyViewModel
import org.whatsong.project.viewmodels.LoginViewModel
import org.whatsong.project.viewmodels.UserViewModel

fun main() {
    val WHATSONG_BASE_URL = "https://"


    val tokenStorage = createTokenStorage()
    val apiClient = whatsongApis(WHATSONG_BASE_URL,tokenStorage)
    val userRepository = UserRepository(apiClient)
    val authRepository = AuthRepository(apiClient)
    val lobbyRepository = LobbyRepository(apiClient)


    val loginViewModel = LoginViewModel(authRepository, tokenStorage)
    val userViewModel = UserViewModel(userRepository)
    val lobbyViewModel = LobbyViewModel(lobbyRepository)


    renderComposable(rootElementId = "root") {
        App(
            loginViewModel = loginViewModel,
            userViewModel = userViewModel,
            lobbyViewModel = lobbyViewModel,
            tokenStorage = tokenStorage
        )
    }
}