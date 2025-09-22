package org.whatsong.project.component.RefreshButton

import org.jetbrains.compose.web.css.*

object RefreshButtonStyles : StyleSheet() {
    val RefreshButton by style {
        position(Position.Absolute)
        top(8.percent)
        right(5.3.percent)
        borderRadius(15.px)
        background("transparent")
        border(0.px)
        cursor("pointer")
        padding(0.px)


    }

    val RefreshImg by style {
        width(160.px)
    }
}