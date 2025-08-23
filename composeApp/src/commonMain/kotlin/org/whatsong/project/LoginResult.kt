package org.whatsong.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class LoginResult(val success: Boolean, val message: String)

class LoginState {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var message by mutableStateOf("")

    fun login(): LoginResult {
        return if (username == "test" && password == "1234") {
            message = "로그인 성공"
            LoginResult(success = true, message = message)
        } else {
            message = "아이디 또는 비밀번호가 틀렸습니다"
            LoginResult(success = false, message = message)
        }
    }
}

