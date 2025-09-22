package org.whatsong.project.page

import org.jetbrains.compose.web.css.*

object LoginStyles : StyleSheet() {

    // 전체 배경
    val background by style {
        width(100.vw)
        height(100.vh)
        backgroundColor(Color("#a7d8f0"))
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
    }

    // 카드 컨테이너
    val card by style {
        backgroundColor(Color.white)
        padding(32.px)
        borderRadius(16.px)
        property("box-shadow", "0px 4px 12px rgba(0, 0, 0, 0.1)")
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        gap(12.px)
        minWidth(280.px)
    }

    // 로고 텍스트
    val logo by style {
        color(Color("#005577"))
        fontSize(28.px)
        marginBottom(16.px)
    }

    // 입력창 스타일
    val inputField by style {
        padding(10.px)
        width(100.percent)
        borderRadius(8.px)
        border {
            style(LineStyle.Solid)
            color(Color.lightgray)
            width(1.px)
        }
    }

    // 버튼 공통
    val button by style {
        width(100.percent)
        padding(12.px)
        borderRadius(8.px)
        color(Color.white)
        fontSize(16.px)
        border {
            style(LineStyle.None)
        }
    }

    // 버튼 색상 변형
    val loginBtn by style {
        backgroundColor(Color("#3399cc"))
    }

    val guestBtn by style {
        backgroundColor(Color("#77bbee"))
    }

    val socialBtn by style {
        backgroundColor(Color("#55aa88"))
    }

    val signupBtn by style {
        backgroundColor(Color("#ffaa55"))
    }
}