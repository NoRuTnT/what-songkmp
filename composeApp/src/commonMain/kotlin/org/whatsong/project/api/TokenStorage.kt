package org.whatsong.project.api

interface TokenStorage {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun setAccessToken(token: String)
    fun setRefreshToken(token: String)
    fun clearTokens()
}

expect fun createTokenStorage(): TokenStorage