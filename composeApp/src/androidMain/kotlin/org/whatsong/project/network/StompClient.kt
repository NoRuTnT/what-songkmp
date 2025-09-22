package org.whatsong.project.network

actual class StompClient {
    actual fun connect() {
    }

    actual fun send(destination: String, body: String) {
    }

    actual fun subscribe(destination: String, callback: (String) -> Unit) {
    }
}