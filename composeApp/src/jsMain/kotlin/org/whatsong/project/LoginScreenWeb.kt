package org.whatsong.project

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*


@Composable
fun LoginScreenWeb(loginState: LoginState,
                   onLoginSuccess: () -> Unit) {
    Style(LoginStyles) //스타일 적용 선언

    Div({ classes(LoginStyles.background) }) {
        Div({ classes(LoginStyles.card) }) {

            H1({ classes(LoginStyles.logo) }) {
                Text("whatsong")
            }

            Input(type = InputType.Text, attrs = {
                value(loginState.username)
                onInput { loginState.username = it.value }
                placeholder("아이디 입력")
                classes(LoginStyles.inputField)
            })

            Input(type = InputType.Password, attrs = {
                value(loginState.password)
                onInput { loginState.password = it.value }
                placeholder("비밀번호 입력")
                classes(LoginStyles.inputField)
            })

            Button(attrs = {
                onClick {
                    val result = loginState.login()
                    if (result.success) {
                        onLoginSuccess() // 화면 전환
                    }
                }
                classes(LoginStyles.button, LoginStyles.loginBtn)
            }) { Text("로그인") }

            Button(attrs = {
                classes(LoginStyles.button, LoginStyles.guestBtn)
            }) { Text("게스트 로그인") }

            Button(attrs = {
                classes(LoginStyles.button, LoginStyles.socialBtn)
            }) { Text("소셜 로그인") }

            Button(attrs = {
                classes(LoginStyles.button, LoginStyles.signupBtn)
            }) { Text("회원가입") }

            if (loginState.message.isNotEmpty()) {
                P { Text(loginState.message) }
            }
        }
    }
}