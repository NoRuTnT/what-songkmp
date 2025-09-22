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
import org.whatsong.project.api.repository.AuthRepository
import org.whatsong.project.api.repository.ResultWrapper
import org.whatsong.project.models.LoginRequestDto
import org.whatsong.project.states.LoginState

class LoginViewModel (
    private val repository: AuthRepository,
    private val tokenStorage: TokenStorage = createTokenStorage()
): ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun onLoginIdChange(value: String) {
        _loginState.update { it.copy(loginId = value) }
    }

    fun onPasswordChange(value: String) {
        _loginState.update { it.copy(password = value) }
    }

    fun login() {
        val loginId = _loginState.value.loginId
        val password = _loginState.value.password

        viewModelScope.launch {
            when (val result = repository.login(LoginRequestDto(loginId, password))) {
                is ResultWrapper.Success -> {
                    val data = result.data

                    tokenStorage.setAccessToken(data.accessToken)
                    tokenStorage.setRefreshToken(data.refreshToken)

                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true
                        )
                    }
                }
                is ResultWrapper.Failure -> {
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            message = "로그인 실패"
                        )
                    }
                }
            }
        }
    }

    fun guestLogin() {
        val password = ""

        viewModelScope.launch {
            val loginId = when (val result = repository.guestLogin()) {
                is ResultWrapper.Success -> result.data.nickname
                is ResultWrapper.Failure -> {
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            message = "게스트 로그인 실패"
                        )
                    }
                    return@launch
                }
            }

            when (val result = repository.login(LoginRequestDto(loginId, password))) {
                is ResultWrapper.Success -> {
                    val data = result.data

                    tokenStorage.setAccessToken(data.accessToken)
                    tokenStorage.setRefreshToken(data.refreshToken)

                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true
                        )
                    }
                }
                is ResultWrapper.Failure -> {
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            message = "로그인 실패"
                        )
                    }
                }
            }
        }
    }


}