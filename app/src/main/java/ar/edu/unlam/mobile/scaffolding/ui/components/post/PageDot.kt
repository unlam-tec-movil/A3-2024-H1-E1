package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageDot(
    listSize: Int,
    pagerState: PagerState,
    scope: CoroutineScope,
) {
    Row(
        modifier =
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(listSize) { it ->
            val color =
                if (pagerState.currentPage == it) Color.DarkGray else Color.LightGray
            Box(
                modifier =
                    Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(20.dp)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        },
            )
        }
    }
}
