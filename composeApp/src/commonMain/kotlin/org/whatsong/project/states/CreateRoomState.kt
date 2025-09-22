package org.whatsong.project.states

data class CreateRoomState(
    val isModalOpen: Boolean = false,
    val roomName: String = "",
    val password: String = "",
    val musicYear: List<String> = emptyList(),
    val quizAmount: Int = 0,
    val maxUserNumber: Int = 1,
    val isPrivate: Boolean = false,
    val errors: Map<String, String> = emptyMap()
)