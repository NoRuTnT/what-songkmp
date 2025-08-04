package org.whatsong.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform