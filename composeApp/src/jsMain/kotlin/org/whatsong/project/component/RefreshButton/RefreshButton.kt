package org.whatsong.project.component.RefreshButton

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Button

@Composable
fun RefreshButton(onClick: () -> Unit) {
    Style(RefreshButtonStyles)

    var hover by remember { mutableStateOf(false) }

    Button(
        attrs = {
            onClick { onClick() }
            onMouseEnter { hover = true }
            onMouseLeave { hover = false }
            classes(RefreshButtonStyles.RefreshButton)
        }
    ) {
//        Img(
//            src = if (hover) "assets/svg/Lobby/hoverRefreshButton.svg"
//            else "assets/svgs/Lobby/refreshButton.svg",
//            attrs = {
//                attr("alt", "refresh button")
//                classes(RefreshButtonStyles.RefreshImg)
//            }
//        )
    }
}