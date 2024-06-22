package ar.edu.unlam.mobile.scaffolding

import android.graphics.Bitmap
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AtomicReference
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
    fun testCarrousel_onItemSelected() {
        val pixel = IntArray(100 * 100)
        val bitmap1 = Bitmap.createBitmap(pixel, 100, 100, Bitmap.Config.ARGB_8888)
        val bitmap2 = Bitmap.createBitmap(pixel, 100, 100, Bitmap.Config.ARGB_8888)
        val bitmap3 = Bitmap.createBitmap(pixel, 100, 100, Bitmap.Config.ARGB_8888)
        val images = listOf(bitmap1, bitmap2, bitmap3)

        // AtomicReference to capture the selected bitmap
        val selectedBitmap = AtomicReference<Bitmap?>()

        composeTestRule.setContent {
            Carrousel(
                listOfImage = images,
                paddingValues = 80.dp,
                onSelectedITem = { bitmap ->
                    selectedBitmap.set(bitmap)
                },
            )
        }
        composeTestRule
            .onNodeWithContentDescription("1", useUnmergedTree = true, ignoreCase = true)
            .performClick()

        // Check that the onSelectedITem callback was called with the correct bitmap
        assert(selectedBitmap.get() == bitmap2) {
            "Expected bitmap2 to be selected, but got ${selectedBitmap.get()}"
        }
    }
}
