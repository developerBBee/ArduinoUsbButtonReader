package jp.developerbbee.arduino.robot

import jp.developerbbee.arduino.data.InputKey
import java.awt.Robot
import java.awt.event.KeyEvent

class MyRobot {
    private val robot by lazy { Robot() }

    fun performKeyAction(key: InputKey) {
        try {
            when (key) {
                InputKey.NONE -> Unit
                InputKey.COPY_KEY -> {
                    robot.keyPress(KeyEvent.VK_META) // command key
                    robot.keyPress(KeyEvent.VK_C)
                    robot.keyRelease(KeyEvent.VK_C)
                    robot.keyRelease(KeyEvent.VK_META)
                }
            }
        } catch (e: Exception) {
            println("Cannot perform key action: $key")
            e.printStackTrace()
        }
    }
}