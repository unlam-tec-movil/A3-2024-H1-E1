package ar.edu.unlam.mobile.scaffolding.ui.components.Post

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ar.edu.unlam.mobile.scaffolding.ui.screens.PostViewModel

@Composable
fun CardImage(postViewModel: PostViewModel, page:Int, pageOffset: Float, content: @Composable () -> Unit) {
    var showDialog by remember{
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                    .also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
            .clickable {
                showDialog = true
            }
    ) {
        content()
        if(showDialog){
            val transition = updateTransition(targetState = showDialog)

            val scale by transition.animateFloat(
                transitionSpec = {
                    TweenSpec(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                }, label = ""
            ) { showDialog ->
                if (showDialog) 1f else 0f
            }

            val alpha by transition.animateFloat(
                transitionSpec = {
                    TweenSpec(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                }, label = ""
            ) { showDialog ->
                if (showDialog) 1f else 0f
            }
            if (showDialog) {
                SettingImage(
                    postViewModel = postViewModel,
                    page = page,
                    onDissmissButon = { showDialog = false }
                )
            }
        }
    }
}
