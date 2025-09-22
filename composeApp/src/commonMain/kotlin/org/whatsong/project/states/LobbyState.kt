package org.whatsong.project.states

import org.whatsong.project.models.ChannelUsersList
import org.whatsong.project.models.ChatMessage
import org.whatsong.project.models.Room


data class LobbyState(
    val isLoading: Boolean = false,
    val channelNo: Int = 1,
    val rooms: List<Room> = emptyList(),
    val users: List<ChannelUsersList> = emptyList(),
    val chatMessages: List<ChatMessage> = emptyList(),
    val error: String? = null,
    val refreshKey: Int = 0
)