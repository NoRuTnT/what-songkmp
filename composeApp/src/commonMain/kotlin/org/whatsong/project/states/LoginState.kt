package org.whatsong.project.states

data class LoginState (
    var isLoading: Boolean = false,
    var isLoggedIn: Boolean = false,
    var loginId: String = "",
    var password: String = "",
    var message: String = ""
)