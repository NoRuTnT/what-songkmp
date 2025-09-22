package org.whatsong.project.component.LobbyChatting

import org.jetbrains.compose.web.css.*

object LobbyChattingStyles : StyleSheet() {
    val wrapper by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        height(100.vh)
        width(100.percent)
    }

    val contentsWrapper by style {
        flex(1)
        overflowY("auto")
        padding(8.px)
    }

    val inputWrapper by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        alignItems(AlignItems.Center)
        padding(8.px)
        property("border-top", "1px solid lightgray")
    }

    val input by style {
        flex(1)
        fontSize(14.px)
        padding(8.px)
        border(1.px, LineStyle.Solid, Color.lightgray)
        borderRadius(4.px)
        outline("none")
    }

    val message by style {
        marginTop(4.px)
        fontSize(14.px)
    }
}
