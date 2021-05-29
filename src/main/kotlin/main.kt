import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter

fun main() {
    Preview {
        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }
        scene.render(frameState)
    }
}

class Scene {
    private var sceneEntity = mutableStateListOf<SceneEntity>()
    val targets = mutableListOf<Target>()
    private val spaceShip = SpaceShip()
    val bullets = mutableListOf<Bullet>()

    fun setupScene() {

       sceneEntity.clear()
        repeat(8) {
            targets.add(Target(x = 80f + (it * 100f), y = 60f))
        }
        sceneEntity.addAll(targets)
        sceneEntity.add(spaceShip)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(frameState: State<Long>) {

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
                for (target in targets) {
                    drawTarget(target)
                    target.isDead = bullets.any { it.hits(target) }

                }


                drawSpaceShip(spaceShip)
                for (bullet in bullets) {
                    drawBullet(bullet)
                }
                if (targets.isEmpty()) {
                    repeat(8) {
                        targets.add(
                           Target(x = 80f + (it * 100f), y = 60f)
                        )
                    }
                    sceneEntity.addAll(targets)
                }


            }
        }


    }
}
