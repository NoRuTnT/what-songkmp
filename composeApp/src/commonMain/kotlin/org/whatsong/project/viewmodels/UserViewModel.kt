package org.whatsong.project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.whatsong.project.api.repository.ResultWrapper
import org.whatsong.project.api.repository.UserRepository
import org.whatsong.project.states.UserState

class UserViewModel (
    private val repository: UserRepository
): ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun loadUserInfo(accessToken: String) {
        viewModelScope.launch {
            when (val result = repository.getUserInfo(accessToken)) {
                is ResultWrapper.Success -> {
                    _userState.update{
                        it.copy(
                            nickname = result.data.nickname,
                            exp = result.data.exp,
                            level = result.data.level,
                            wins = result.data.wins
                        )
                    }
                }
                is ResultWrapper.Failure -> {
                    println(" ${result.cause}")
                }
            }
        }
    }
}