package ar.edu.unlam.mobile.scaffolding.ui.components.Post


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carrousel(
    /*pasar como parametros las acciones y la lista */
    listOfImage: MutableList<String>,
    onSelectedITem:(String)->Unit = {},
){
    val pagerState = rememberPagerState(initialPage = 1)
    ///me falta ver en el caso de que este vacio la lista, xq no muestra nada
    HorizontalPager(
        count = listOfImage.size,
        state = pagerState,
    ) { page ->
        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
        /*si el tamaño de la lista es o entonces muestro un boton de agregar fotos */
        CarrouselItem(
            item = listOfImage[page],
            pageOffset,
            onItemClick = { selectedItem -> onSelectedITem(selectedItem) }
        )
    }

}
@Preview
@Composable
fun AdImagePreview(
){
    val lista : MutableList<String> = mutableListOf()
    lista.add( "https://depor.com/resizer/crzkitvd2v51Tsyw_Xc1A4enYVs=/1200x900/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/DAYT2F5NUNB7VPAFKUPHNDXVQA.jpg")
    Carrousel(listOfImage = lista)
}