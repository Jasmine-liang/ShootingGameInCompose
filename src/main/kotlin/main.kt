import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.imageResource

fun main() {
    Preview {
        val assets = listOf(
            imageResource("images/bullet.png"),
            imageResource("images/jetpack.png"),
            imageResource("images/alien.png"),
            imageResource("images/alienW.jpg"),
            imageResource("images/alienY.png")

        )
        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }

        scene.render(frameState, assets)
    }
}

class Scene {
    private var sceneEntity = mutableStateListOf<SceneEntity>()
    val targets = mutableListOf<Target>()
    private val stars = mutableListOf<Star>()
    private val spaceShip = SpaceShip()
    val bullets = mutableListOf<Bullet>()
    var targetCount = 0

    fun setupScene() {

       sceneEntity.clear()

        repeat(targetCount) {
            targets.add(Target(x = 80f + (it * 100f), y = 60f))
        }
        sceneEntity.addAll(targets)

        repeat(800 * 2){
            stars.add(Star())
        }
        sceneEntity.addAll(stars)
        sceneEntity.add(spaceShip)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(frameState: State<Long>, assets: List<ImageBitmap>) {

        Surface(color = Color.White) {
            Canvas(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color.Black)
                    .combinedClickable(
                        onClick = {
                            val bullet = Bullet(spaceShip.x, spaceShip.y)
                            sceneEntity.add(bullet)
                            bullets.add(bullet)
                        }
                    )
                    .pointerMoveFilter(
                        onMove = {
                            val (x, y) = it
                            spaceShip.x = x
                            spaceShip.y = y
                            true
                        }
                    )
            ) {
                val stepFrame = frameState.value

                for (star in stars){
                    drawStar(star)
                }

                var nameCounter = 2
                for ((index, target) in targets.withIndex()){
                    val  targetBitmap = when{
                        index % 2 == 0 -> {
                            nameCounter += 1
                            assets.getOrNull(nameCounter) ?: assets[3]
                        }
                        else -> assets[2]
                    }
                    drawTarget(targetBitmap, target)
                    target.isDead = bullets.any { it.hits(target) }
                }


                drawSpaceShip(assets[1],spaceShip)

                for (bullet in bullets) {
                    drawBullet(assets[0],bullet)
                }
                if (targets.isEmpty()) {
                    targetCount += 2
                    nameCounter = 2
                    repeat(targetCount) {
                        targets.add(Target(x = 80f + (it * 100f), y = 60f))
                    }
                    sceneEntity.addAll(targets)
                }


            }
        }


    }
}
