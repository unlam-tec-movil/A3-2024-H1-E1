package ar.edu.unlam.mobile.scaffolding.ui.components.Post


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddImages(
){
    val pagerState = rememberPagerState(initialPage = 1)
    val sliderList = mutableListOf<String>().apply {
        add("https://picsum.photos/id/1/700/800")
        add("https://picsum.photos/seed/picsum/700/800")
    }.take(3)

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 85.dp),
            modifier = Modifier.height(350.dp)
        ) {
            page->
            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
            if(sliderList.getOrNull(page) == null){
                CardImage( sliderList.getOrNull(page),pageOffset){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }else{
                CardImage(sliderList.getOrNull(page), pageOffset){
                        Box(
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(sliderList[page])
                                    .crossfade(true)
                                    .scale(Scale.FILL)
                                    .build(),
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.loading_image) ,
                                error = painterResource(id = R.drawable.images_error)
                            )
                        }
                }
            }

        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3){it->
                val color = if(pagerState.currentPage == it) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(20.dp)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DialogImagePreview(){
    var showDialog by remember {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        SettingImage(image = "https://picsum.photos/id/1/700/800",
            onDissmissButon = { showDialog = false },
            onTakePicture = { /**/ },
            onUploadImage = {}) {
        }
    }

}
@Preview
@Composable
fun AdImagePreview(
){
    AddImages()
}