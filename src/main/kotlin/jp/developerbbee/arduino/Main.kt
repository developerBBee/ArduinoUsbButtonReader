package jp.developerbbee.arduino

import com.fazecast.jSerialComm.SerialPort
import jp.developerbbee.arduino.data.InputKey
import jp.developerbbee.arduino.robot.MyRobot
import jp.developerbbee.arduino.serial.SerialFunctions
import jp.developerbbee.arduino.serial.SerialScanner
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() {
    try {
        val serialPorts = SerialFunctions.getAvailablePorts()
        println("Available ports: ${serialPorts.map { it.systemPortName }}")

        val port = serialPorts.firstOrNull { it.systemPortName == SerialFunctions.MAC_OS_PORT_NAME }
            ?.apply {
                println("Connecting to $systemPortName")
                baudRate = 115200
                openPort()
                setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
            }
            ?: throw IllegalStateException("Matching port not found")

        if (port.isOpen) {
            println("Port opened successfully")
            val myRobot = MyRobot()

            SerialScanner(port).use {
                runBlocking {
                    it.startAndGetScanTextFlow()
                        .catch { e ->
                            println("Read error occurred: $e")
                            throw e
                        }
                        .map { InputKey.from(it) }
                        .collect {
                            println("Received data: $it")
                            myRobot.performKeyAction(it)
                        }
                }
            }
        } else {
            println("Port is not opened")
        }
    } catch (e: Exception) {
        println("Cannot connect to Arduino")
        e.printStackTrace()
    }
}
