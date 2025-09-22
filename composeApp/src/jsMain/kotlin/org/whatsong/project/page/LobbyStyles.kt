package org.whatsong.project.page

import org.jetbrains.compose.web.css.*

object LobbyStyles : StyleSheet() {

    val page by style {
        width(100.vw)
        height(100.vh)
        backgroundColor(Color("#f8f9fb"))
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
    }

    val header by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.FlexEnd)
        padding(16.px)
        gap(12.px)
        backgroundColor(Color.white)
        property("box-shadow", "0px 2px 6px rgba(0, 0, 0, 0.1)")

    }

    val button by style {
        padding(8.px, 14.px)
        borderRadius(6.px)
        border {
            style(LineStyle.Solid)
            color(Color.lightgray)
            width(1.px)
        }
        backgroundColor(Color.white)
        cursor("pointer")
    }

    val greenButton by style {
        backgroundColor(Color("#16a34a"))
        color(Color.white)
        border {
            style(LineStyle.None)
        }
    }

    val container by style {
        flex(1)
        display(DisplayStyle.Flex)
        padding(16.px)
        gap(16.px)
    }

    val sidebar by style {
        width(240.px)
        backgroundColor(Color.white)
        borderRadius(8.px)
        padding(12.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(12.px)

    }

    val userList by style {
        flex(1)
        overflowY("auto")
        border {
            style(LineStyle.Solid)
            color(Color("#e5e7eb"))
            width(1.px)
        }
        borderRadius(6.px)
        padding(8.px)
    }

    val userItem by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.SpaceBetween)
        padding(4.px)
    }

    val profileBox by style {
        backgroundColor(Color.white)
        borderRadius(8.px)
        padding(12.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(8.px)

    }

    val mainArea by style {
        flex(1)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(12.px)
    }

    val roomCard by style {
        backgroundColor(Color.white)
        borderRadius(8.px)
        padding(16.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(8.px)

    }

    val roomHeader by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.SpaceBetween)
        alignItems(AlignItems.Center)
    }

    val badge by style {
        padding(2.px, 6.px)
        borderRadius(4.px)
        fontSize(12.px)
        backgroundColor(Color("#f87171"))
        color(Color.white)
    }
}