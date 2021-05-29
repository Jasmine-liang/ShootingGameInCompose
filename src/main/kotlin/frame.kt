import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun StepFrame(): State<Long>{
    val millis = remember{ mutableStateOf(0L)}
     LaunchedEffect(Unit){
        val startTime = withFrameNanos { it }
        while (true){
            withFrameMillis {
                frameTime ->
                    millis.value = frameTime - startTime
            }
            //callback.invoke()
        }
    }

    return millis
}