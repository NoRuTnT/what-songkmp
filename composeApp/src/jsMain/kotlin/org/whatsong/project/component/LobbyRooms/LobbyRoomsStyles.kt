package org.whatsong.project.component.LobbyRooms

import org.jetbrains.compose.web.css.*

object LobbyRoomsStyles : StyleSheet() {

    // 모달 오버레이
    val modalOverlay by style {
        position(Position.Fixed)
        top(0.px)
        left(0.px)
        width(100.vw)
        height(100.vh)
        backgroundColor(rgba(0, 0, 0, 0.5))
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        property("z-index", "1000")
    }

    // 모달 컨테이너
    val modalContainer by style {
        backgroundColor(Color.white)
        padding(40.px)
        borderRadius(20.px)
        position(Position.Relative)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        gap(20.px)
        property("box-shadow", "0 10px 25px rgba(0,0,0,0.2)")
    }

    // 모달 로고
    val modalLogo by style {
        width(200.px)
        marginBottom(20.px)
    }

    // 모달 비밀번호 입력
    val modalPasswordInput by style {
        padding(15.px)
        border(2.px, LineStyle.Solid, Color("#ddd"))
        borderRadius(10.px)
        fontSize(16.px)
        width(200.px)
        textAlign("center")
    }

    // 모달 버튼 컨테이너
    val modalButtonContainer by style {
        display(DisplayStyle.Flex)
        gap(10.px)
    }

    // 모달 확인 버튼
    val modalConfirmButton by style {
        padding(10.px, 20.px)
        backgroundColor(Color("#4CAF50"))
        color(Color.white)
        border(0.px)
        borderRadius(5.px)
        fontSize(16.px)
        cursor("pointer")
    }

    // 모달 취소 버튼
    val modalCancelButton by style {
        padding(10.px, 20.px)
        backgroundColor(Color("#f44336"))
        color(Color.white)
        border(0.px)
        borderRadius(5.px)
        fontSize(16.px)
        cursor("pointer")
    }

    // 로비 컨테이너
    val lobbyContainer by style {
        position(Position.Relative)
        padding(20.px)
    }

    // 로딩 컨테이너
    val loadingContainer by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(400.px)
        fontSize(18.px)
    }

    // 에러 컨테이너
    val errorContainer by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(400.px)
        fontSize(16.px)
        color(Color.red)
    }

    // 빈 방 컨테이너
    val emptyRoomContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        height(400.px)
        padding(20.px)
    }

    // 빈 방 아이콘
    val emptyRoomIcon by style {
        width(500.px)
        opacity(0.5)
    }

    // 방 목록 그리드
    val roomGrid by style {
        display(DisplayStyle.Grid)
        gridTemplateColumns("repeat(auto-fit, minmax(300px, 1fr))")
        gap(20.px)
        marginBottom(40.px)
    }

    // 방 카드 (활성 상태)
    val roomCardActive by style {
        backgroundColor(rgba(255, 255, 255, 0.8))
        borderRadius(15.px)
        padding(20.px)
        cursor("pointer")
        border(2.px, LineStyle.Solid, Color("#ddd"))
        property("box-shadow", "0 4px 8px rgba(0,0,0,0.1)")
        property("transition", "all 0.3s ease")

        hover {
            property("transform", "scale(1.02)")
            property("box-shadow", "0 6px 12px rgba(0,0,0,0.15)")
        }
    }

    // 방 카드 (비활성 상태 - 게임 중)
    val roomCardInactive by style {
        backgroundColor(rgba(0, 0, 0, 0.5))
        borderRadius(15.px)
        padding(20.px)
        cursor("not-allowed")
        border(2.px, LineStyle.Solid, Color("#ddd"))
        property("box-shadow", "0 4px 8px rgba(0,0,0,0.1)")
        property("transition", "all 0.3s ease")
    }

    // 방 번호
    val roomNumber by style {
        fontSize(24.px)
        fontWeight("bold")
        color(Color("#333"))
        marginBottom(10.px)
    }

    // 방 제목
    val roomTitle by style {
        fontSize(18.px)
        fontWeight("bold")
        marginBottom(8.px)
        color(Color("#555"))
    }

    // 방 정보 (방장, 연도)
    val roomInfo by style {
        fontSize(14.px)
        color(Color("#666"))
        marginBottom(8.px)
    }

    // 방 하단 정보 컨테이너
    val roomBottomInfo by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.SpaceBetween)
        alignItems(AlignItems.Center)
        marginTop(15.px)
    }

    // 인원수 (정상)
    val memberCountNormal by style {
        fontSize(16.px)
        fontWeight("bold")
        color(Color("#333"))
    }

    // 인원수 (만석)
    val memberCountFull by style {
        fontSize(16.px)
        fontWeight("bold")
        color(Color.red)
    }

    // 잠금 아이콘
    val lockIcon by style {
        width(30.px)
        height(30.px)
    }

    // 문제 수
    val quizCount by style {
        fontSize(14.px)
        color(Color("#666"))
    }

    // 페이지네이션 컨테이너
    val paginationContainer by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        gap(20.px)
        alignItems(AlignItems.Center)
    }

    // 페이지네이션 버튼 (활성)
    val paginationButtonActive by style {
        backgroundColor(Color.transparent)
        border(0.px)
        cursor("pointer")
        opacity(1.0)
        padding(10.px)
        borderRadius(50.percent)
    }

    // 페이지네이션 버튼 (비활성)
    val paginationButtonDisabled by style {
        backgroundColor(Color.transparent)
        border(0.px)
        cursor("not-allowed")
        opacity(0.3)
        padding(10.px)
        borderRadius(50.percent)
    }

    // 페이지네이션 아이콘
    val paginationIcon by style {
        width(60.px)
        height(60.px)
    }

    // 페이지네이션 이전 버튼 아이콘
    val paginationPrevIcon by style {
        width(60.px)
        height(60.px)
        property("transform", "rotate(180deg)")
    }

    // 페이지 정보
    val pageInfo by style {
        fontSize(16.px)
        color(Color("#333"))
        fontWeight("bold")
    }
}