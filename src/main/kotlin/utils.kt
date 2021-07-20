import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize


fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Window(
        title = "ShootGame",
        size = IntSize(Window.WIDTH, Window.HEIGHT),
        resizable = false,
        centered = true

    ){
        MaterialTheme (){
            Surface(modifier = modifier.fillMaxSize()) {
                content()
            }
        }
    }
}

fun Float.mapRange(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}

