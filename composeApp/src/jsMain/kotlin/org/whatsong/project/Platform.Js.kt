package org.whatsong.project

class JSPlatform: Platform {
    override val name: String = "Web with Kotlin/JS(IR)"
}

actual fun getPlatform(): Platform = JSPlatform()