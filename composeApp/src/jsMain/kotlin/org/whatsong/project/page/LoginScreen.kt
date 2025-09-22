package org.whatsong.project.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.*
import org.whatsong.project.viewmodels.LoginViewModel


@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Style(LoginStyles)
    val state by loginViewModel.loginState.collectAsState()

    Div({ classes(LoginStyles.background) }) {
        Div({ classes(LoginStyles.card) }) {

            H1({ classes(LoginStyles.logo) }) {
                Text("whatsong")
            }

            Input(type = InputType.Text, attrs = {
                value(state.loginId)
                onInput { e -> loginViewModel.onLoginIdChange(e.value) }
                placeholder("아이디 입력")
                classes(LoginStyles.inputField)
            })

            Input(type = InputType.Password, attrs = {
                value(state.password)
                onInput { e -> loginViewModel.onPasswordChange(e.value) }
                placeholder("비밀번호 입력")
                classes(LoginStyles.inputField)
            })

            Button(attrs = {
                onClick {
                    loginViewModel.login()
                }
                classes(LoginStyles.button, LoginStyles.loginBtn)
            }) { Text(if (state.isLoading) "로그인 중.." else "로그인") }

            Button(attrs = {
                onClick {
                    loginViewModel.guestLogin()
                }
                classes(LoginStyles.button, LoginStyles.guestBtn)
            }) { Text("게스트 로그인") }

            Button(attrs = {
                classes(LoginStyles.button, LoginStyles.socialBtn)
            }) { Text("소셜 로그인") }

            Button(attrs = {
                classes(LoginStyles.button, LoginStyles.signupBtn)
            }) { Text("회원가입") }

            if (state.message!!.isNotEmpty()) {
                P { state.message?.let { Text(it) } }
            }


        }
    }
}