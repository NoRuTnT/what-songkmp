package org.whatsong.project.navigation

sealed class Screen {
    object Login : Screen()
    object Lobby : Screen()
//    data class Game(val roomNo: Int) : Screen()
}