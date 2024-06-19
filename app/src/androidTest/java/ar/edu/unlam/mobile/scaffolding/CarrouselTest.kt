package ar.edu.unlam.mobile.scaffolding

import android.graphics.Bitmap
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarrouselTest {
    // /creamos una regla para el compose
    @get:Rule
    var composeTestRule = createComposeRule()

    @Test
    fun testCarrousel() {
        val pixel = IntArray(100 * 100)
        val bitmap = Bitmap.createBitmap(pixel, 100, 100, Bitmap.Config.ARGB_8888)
        val images = listOf(bitmap, bitmap, bitmap)

        composeTestRule.setContent {
            Carrousel(
                listOfImage = images,
                paddingValues = 80.dp,
            )
        }
        // /verificamos que el carrousel se muestra correctamente
        images.forEachIndexed { index, bitmap ->
            composeTestRule.onRoot().printToLog("CARROUSEL_TEST") // Useful for debugging
            composeTestRule
                .onNodeWithContentDescription("Image $index")
                .assertExists()
        }
    }
}
