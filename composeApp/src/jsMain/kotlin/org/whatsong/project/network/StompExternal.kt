@file:JsModule("@stomp/stompjs")
@file:JsNonModule
package org.whatsong.project.network

import kotlin.js.Json

external class Client(config: Json = definedExternally){
    fun activate()
    fun deactivate()
    fun publish(params: dynamic)
    fun subscribe(destination: String, callback: (IMessage) -> Unit): dynamic
}

external interface IMessage {
    val body: String
}