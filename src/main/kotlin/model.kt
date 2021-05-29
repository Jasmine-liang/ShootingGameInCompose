import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class SceneEntity{
    abstract fun update(scene: Scene)
}

data class Target(
    var x: Float = 100f,
    var y: Float = 100f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.Yellow, Color.Magenta).random()

): SceneEntity(){
    override fun update(scene: Scene) {
        TODO("Not yet implemented")
    }

}

fun DrawScope.drawTarget(target: Target){
//    val canvasWidth = size.width
//    val canvasHeight = size.height
//    val centerX = canvasWidth / 2
//    val centerY = canvasHeight / 2

    drawCircle(
        color = target.color,
        radius = 50f,
        center = Offset(target.x, target.y)
    )
}


data class SpaceShip(
    val x: Float,
    val y: Float
): SceneEntity(){
    override fun update(scene: Scene) {
        TODO("Not yet implemented")
    }
}



fun DrawScope.drawSpaceShip(mouseXY: Pair<Float, Float>){
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    drawRect(
        color = shipColor,
        size = Size(50f, 80f),
        topLeft = Offset(mouseXY.first - 25f, canvasHeight - 80f)

    )

}