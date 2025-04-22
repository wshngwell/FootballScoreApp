package com.example.footballscoreapp.presentation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.example.footballscoreapp.presentation.theme.scrollBarColor


fun Modifier.myVerticalScrollBar(
    listState: LazyListState
): Modifier {
    return drawWithContent {
        drawContent()
        val elementHeight =
            this.size.height / listState.layoutInfo.totalItemsCount
        val scrollbarOffsetY = listState.firstVisibleItemIndex * elementHeight
        val scrollbarHeight =
            listState.layoutInfo.visibleItemsInfo.size * elementHeight

        drawRoundRect(
            color = scrollBarColor,
            topLeft = Offset(
                this.size.width - 5.dp.toPx(),
                scrollbarOffsetY
            ),
            cornerRadius = CornerRadius(3f, 3f),
            size = Size(5.dp.toPx(), scrollbarHeight),
        )
    }
}