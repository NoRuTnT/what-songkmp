package org.whatsong.project.network

import kotlin.js.json

external interface PublishParams {
    var destination: String
    var body: String?
    var headers: dynamic
}

actual class StompClient actual constructor() {
    private var client: Client? = null

    actual fun connect(
        brokerUrl: String,
        accessToken: String,
        channelNo: String,
        connectType: String,
    ) {
        client = Client(
            json(
                "brokerURL" to brokerUrl,
                "connectHeaders" to json(
                    "accessToken" to accessToken,
                    "channelNo" to channelNo,
                    "connectType" to connectType
                ),
                "onStompError" to { frame: dynamic ->
                    console.error("STOMP Error: ${'$'}{frame.headers.message}")
                }
            )
        )
        client?.activate()
    }

    actual fun disconnect() {
        client?.deactivate()
        client = null
    }

    actual fun send(destination: String, body: String, headers: dynamic) {
        val params = js("{}").unsafeCast<PublishParams>()
        params.destination = destination
        params.body = body
        params.headers = headers

        client?.publish(params)
    }

    actual fun subscribe(destination: String, callback: (String) -> Unit) {
        client?.subscribe(destination) { msg ->
            callback(msg.body)
        }
    }
}