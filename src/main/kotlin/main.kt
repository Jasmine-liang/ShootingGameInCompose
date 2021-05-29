import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun main() {
    Preview {
        val scene = remember { Scene() }
    }
}

class Scene{
    private var sceneEntity = mutableStateListOf<SceneEntity>()

    fun setupScene(){
        sceneEntity.clear()
    }

    fun update(){
        for (entity in sceneEntity){
            entity.update(this)
        }
    }

    @Composable
    fun render(frameState: State<Long>){
         Box {
          Canvas(
              modifier = Modifier.fillMaxSize()
                  .background(color = Color.Black)
          ){
               val stepFrame = frameState.value
           }
         }
    }
}
