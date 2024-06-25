package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carrousel(
    listOfImage: List<Bitmap>,
    paddingValues: Dp, // /cambia el tamaño , a menor valor dp mas ancho , a mayor valor mas angosto
    onSelectedITem: (Bitmap) -> Unit = {},
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val count = if (listOfImage.isEmpty()) 1 else listOfImage.size

        HorizontalPager(
            count = count,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = paddingValues),
            modifier =
                Modifier
                    .height(350.dp)
                    .padding(top = 5.dp),
        ) { page ->
            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
            if (listOfImage.isEmpty()) {
                val bitmapIcon =
                    BitmapFactory.decodeResource(context.resources, R.drawable.default_image_pet)
                CarrouselItem(item = bitmapIcon, pageOffset = pageOffset, index = page) {
                }
            } else {
                CarrouselItem(
                    item = listOfImage[page],
                    pageOffset = pageOffset,
                    index = page,
                ) { selectedItem ->
                    onSelectedITem(selectedItem)
                }
            }
        }
        PageDot(listSize = listOfImage.size, pagerState = pagerState, scope = scope)
    }
}

@Preview(showBackground = true)
@Composable
fun CarrouselPreview() {
    Carrousel(listOfImage = emptyList<Bitmap>(), paddingValues = 5.dp) {
//
    }
}
