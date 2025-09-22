package org.whatsong.project.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json



class whatsongApis (
    val baseUrl: String,
    val tokenStorage: TokenStorage
) {

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(DefaultRequest) {
            header("Accept", "application/json")
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }

    suspend inline fun <reified T> safeRequest(
        crossinline block: suspend HttpClient.() -> HttpResponse
    ): T {
        return try {
            val response = client.block()
            response.body()
        } catch (e: ResponseException) {
            val response = e.response
            val errorBody: Map<String, Any>? = try { response.body() } catch (_: Throwable) { null }
            val errorCode = errorBody?.get("code") as? Int

            when (errorCode) {
                1050 -> {
                    val refreshToken = tokenStorage.getRefreshToken() ?: throw e
                    val newTokens = refreshTokens(refreshToken) ?: throw e
                    val (newAccess, newRefresh) = newTokens
                    tokenStorage.setAccessToken(newAccess)
                    tokenStorage.setRefreshToken(newRefresh)
                    client.block().body()
                }
                1051 -> {
                    tokenStorage.clearTokens()
                    throw e
                }
                else -> throw e
            }
        }
    }


    suspend inline fun <reified Res> get(
        path: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}): Res =
        safeRequest { client.get("$baseUrl$path") }

    suspend inline fun <reified Res> post(
        path: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}): Res =
        safeRequest {
            client.post("$baseUrl$path") {
                block()
            }
        }

    suspend inline fun <reified Req, reified Res> post(
        path: String,
        body: Req,
        noinline block: HttpRequestBuilder.() -> Unit = {}): Res =
        safeRequest {
            client.post("$baseUrl$path") {
                setBody(body)
            }
        }

    suspend inline fun <reified Req, reified Res> put(
        path: String,
        body: Req,
        noinline block: HttpRequestBuilder.() -> Unit = {}): Res =
        safeRequest {
            client.put("$baseUrl$path") {
                setBody(body)
            }
        }

    suspend inline fun <reified Res> delete(
        path: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}): Res =
        safeRequest { client.delete("$baseUrl$path") }

    // refreshToken 요청
    suspend fun refreshTokens(refreshToken: String): Pair<String, String>? {
        val res: Map<String, Map<String, String>> = client.post("$baseUrl/member/token") {
            header("refreshToken", refreshToken)
        }.body()

        val data = res["data"] ?: return null
        val access = data["accessToken"] ?: return null
        val refresh = data["refreshToken"] ?: return null
        return access to refresh
    }
}
