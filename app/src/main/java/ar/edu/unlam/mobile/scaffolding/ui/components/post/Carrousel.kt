package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> Carrousel(
    listOfImage: List<T>,
    onSelectedITem: (T) -> Unit = {},
) {
    val pagerState = rememberPagerState(initialPage = 0)
    HorizontalPager(
        count = listOfImage.size,
        state = pagerState,
    ) { page ->
        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
        CarrouselItem(
            item = listOfImage[page],
            pageOffset,
        ) {
                selectedItem ->
            onSelectedITem(selectedItem)
        }
    }
}
