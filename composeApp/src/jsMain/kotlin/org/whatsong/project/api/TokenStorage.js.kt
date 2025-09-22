package org.whatsong.project.api

import kotlinx.browser.window

actual fun createTokenStorage(): TokenStorage = WebTokenStorage()

class WebTokenStorage : TokenStorage {
    override fun getAccessToken(): String? =
        window.localStorage.getItem("UAT")

    override fun getRefreshToken(): String? =
        window.localStorage.getItem("URT")

    override fun setAccessToken(token: String) {
        window.localStorage.setItem("UAT", token)
    }

    override fun setRefreshToken(token: String) {
        window.localStorage.setItem("URT", token)
    }

    override fun clearTokens() {
        window.localStorage.removeItem("UAT")
        window.localStorage.removeItem("URT")
//        window.localStorage.removeItem("nickname")
    }
}