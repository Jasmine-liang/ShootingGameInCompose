import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.pow
import kotlin.math.sqrt

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}

data class Target(
    var x: Float,
    var y: Float,
    val radius: Float = 30f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.Yellow, Color.Magenta).random(),
    var isDead: Boolean = false
) : SceneEntity() {

    var canvasWidth: Float = Window.WIDTH_VALUE
    private var horizontalDirection = 5
    private var verticalDirection = 0

    private val edge: Float
        get() = canvasWidth

    override fun update(scene: Scene) {
        if (isDead) {
            scene.targets.remove(this)

        }

        if (x == edge || x == 0f){
            //when the target reach the edge of the window, 让他们反向弹回来
            horizontalDirection *= -1
            // 弹回来的同时向下移动一点
            y  += radius / 2

        }

        x += horizontalDirection
        y += verticalDirection
    }

}

fun DrawScope.drawTarget(target: Target) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    target.canvasWidth = canvasWidth

    drawCircle(
        color = target.color,
        radius = target.radius,
        center = Offset(target.x, target.y)
    )
}


data class SpaceShip(
    var x: Float = 0f,
    var y: Float = 0f
) : SceneEntity() {
    override fun update(scene: Scene) {

    }
}


fun DrawScope.drawSpaceShip(spaceShip: SpaceShip) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    spaceShip.y = canvasHeight - 80f

    drawRect(
        color = shipColor,
        size = Size(50f, 80f),
        topLeft = Offset(spaceShip.x - 25f, spaceShip.y)

    )

}

data class Bullet(
    var x: Float = 0f,
    var y: Float = 0f,
) : SceneEntity() {
    override fun update(scene: Scene) {
        if (y < 0) {
            //clean up the bullet
            scene.bullets.remove(this)
        }
        y -= 10
    }

    fun hits(target: Target): Boolean{
        val distance = sqrt((y - target.y).toDouble().pow(2) + (x - target.x).toDouble().pow(2))
        return distance < (target.radius * 2)
    }
}


fun DrawScope.drawBullet(bullet: Bullet) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    drawRect(
        color = shipColor,
        size = Size(16f, 16f),
        topLeft = Offset(bullet.x, bullet.y)
    )

}