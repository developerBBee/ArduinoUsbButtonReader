package jp.developerbbee.arduino.serial

import com.fazecast.jSerialComm.SerialPort

object SerialFunctions {
    const val MAC_OS_PORT_NAME = "cu.usbmodem2101"

    fun getAvailablePorts(): List<SerialPort> {
        return SerialPort.getCommPorts().filterNotNull().toList()
    }
}