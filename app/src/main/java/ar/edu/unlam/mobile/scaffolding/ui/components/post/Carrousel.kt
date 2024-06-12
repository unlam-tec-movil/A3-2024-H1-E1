package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
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
    val pagerState = rememberPagerState(initialPage = 1)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (listOfImage.isEmpty()) {
            HorizontalPager(
                count = 1,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 80.dp),
                modifier =
                    Modifier
                        .height(350.dp)
                        .padding(top = 5.dp),
            ) { page ->
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                val bitmapIcon = BitmapFactory.decodeResource(context.resources, R.drawable.icon_subir_magen)
                CarrouselItem(item = bitmapIcon, pageOffset = pageOffset) {
                }
            }
        } else {
            HorizontalPager(
                count = listOfImage.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 80.dp),
                modifier =
                    Modifier
                        .height(350.dp)
                        .padding(top = 5.dp),
            ) { page ->
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                CarrouselItem(
                    item = listOfImage[page],
                    pageOffset,
                ) { selectedItem ->
                    onSelectedITem(selectedItem)
                }
            }
            PageDot(listSize = listOfImage.size, pagerState = pagerState, scope = scope)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarrouselPreview() {
    Carrousel(listOfImage = emptyList<String>()) {
//
    }
}
