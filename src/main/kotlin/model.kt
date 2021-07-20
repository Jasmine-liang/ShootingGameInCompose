import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.pow
import kotlin.math.sqrt

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}

fun randomX(canvasWidth: Int) = (0..canvasWidth).random().toFloat()
fun randomValue(max: Int) = (1..max).random().toFloat()
fun randomFallSpeed(z: Float) = z.mapRange(0f to 20f, 4f to 10f)
fun randomY() = (-500..-50).random().toFloat()
fun randomZ()= (1..10).random().toFloat()
fun randomDropThickness(z: Float) = z.mapRange(0f to 20f, 1f to 5f)

data class Star(
    var canvasWidth: Float = Window.WIDTH_VALUE,
    var canvasHeight: Float = Window.HEIGHT_VALUE,
    var x: Float = randomX(canvasWidth.toInt()),
    var y: Float = randomY(),
    var z: Float = randomZ(),
    var length: Float = randomX(3),
    var thickness: Float = randomDropThickness(z)
): SceneEntity(){

    var fallSpeed: Float = randomFallSpeed(z)
    val height get() = canvasHeight

    override fun update(scene: Scene) {
       y += fallSpeed
        if (y > height){
            reset()
        }
    }

    private fun reset(){
        x = randomX(canvasWidth.toInt())
        y = 0f
        length = randomValue(3)
        fallSpeed = randomFallSpeed(z)
        thickness = randomDropThickness(z)
    }

}

fun DrawScope.drawStar(star: Star){
    val canvasWidth = size.width
    val canvasHeight = size.height
    star.canvasWidth = canvasWidth
    star.canvasHeight = canvasHeight
    drawLine(
        color = Color.White,
        start = Offset(star.x, star.y),
        end = Offset(star.x, star.y + star.length),
        strokeWidth = star.thickness
    )
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

fun DrawScope.drawTarget(targetImage: ImageBitmap, target: Target) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    target.canvasWidth = canvasWidth
    drawImage(targetImage, Offset(target.x, target.y))
}


data class SpaceShip(
    var x: Float = 0f,
    var y: Float = 0f
) : SceneEntity() {
    override fun update(scene: Scene) {

    }
}


fun DrawScope.drawSpaceShip(spaceShipImage: ImageBitmap, spaceShip: SpaceShip) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    spaceShip.y = canvasHeight - 80f

    val bitmapX = spaceShip.x - spaceShipImage.width.toFloat() / 2
    val bitmapY = spaceShip.y - spaceShipImage.height.toFloat() / 2

    drawImage(spaceShipImage, Offset(bitmapX, bitmapY))


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


fun DrawScope.drawBullet(bulletImage: ImageBitmap, bullet: Bullet) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    val bitmapX = bullet.x - bulletImage.width.toFloat() / 2
    val bitmapY = bullet.y - bulletImage.height.toFloat() / 2
    drawImage(bulletImage, Offset(bitmapX, bitmapY))

}