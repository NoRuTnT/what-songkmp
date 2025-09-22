package org.whatsong.project.component.LobbyUsersList

import org.jetbrains.compose.web.css.*

object LobbyUsersListStyles : StyleSheet() {

    val wrapper by style {
        property("width", "21rem")
        height(77.vh)
        flexShrink(0.0)
        border {
            style(LineStyle.Solid)
            width(5.px)
            color(rgba(235, 226, 255, 0.4))
        }
        borderRadius(30.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        backgroundColor(rgba(255, 255, 255, 0.5))
        position(Position.Absolute)
        top(16.5.percent)
        left(4.5.percent)
    }

    val listWrapper by style {
        property("width", "17rem")
        height(68.vh)
        border {
            style(LineStyle.Solid)
            width(5.px)
            color(rgba(235, 226, 255, 0.4))
        }
        borderRadius(12.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        backgroundColor(rgba(255, 255, 255, 0.5))
        position(Position.Absolute)
        bottom(2.percent)
        property("overflow-y", "auto")


        property("&::-webkit-scrollbar", "width: 6px")
        property("&::-webkit-scrollbar-thumb", "background: pink; border-radius: 12px")
    }

    val userCell by style {
        fontSize(16.px)
        fontWeight("bold")
        marginBottom(16.px)
        display(DisplayStyle.Flex)
        padding(10.px)
        property("border-bottom", "1px solid #eee")
    }

    val title by style {
        fontSize(20.px)
        fontWeight("bold")
        marginTop(8.percent)
    }
}