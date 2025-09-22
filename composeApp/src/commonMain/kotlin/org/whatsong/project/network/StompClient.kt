package org.whatsong.project.network

expect class StompClient() {
    fun connect(
        brokerUrl: String,
        accessToken: String,
        channelNo: String,
        connectType: String
    )
    fun send(destination: String, body: String, headers: dynamic)
    fun subscribe(destination: String, callback: (String) -> Unit)
    fun disconnect()
}