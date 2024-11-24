package jp.developerbbee.arduino.serial

import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Scanner

class SerialScanner(port: SerialPort) : AutoCloseable {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private val scanner = Scanner(port.inputStream)
    private val _scanTextFlow = MutableStateFlow("")
    private val scanTextFlow: Flow<String> = _scanTextFlow.filter { it.isNotEmpty() }

    fun startAndGetScanTextFlow(): Flow<String> {
        startReadLineJob()
        return scanTextFlow
    }

    private fun startReadLineJob() {
        job?.cancel()
        job = ioScope.launch {
            while (scanner.hasNextLine()) {
                scanner.nextLine().trim().also { receivedData ->
                    _scanTextFlow.emit(receivedData)
                }
            }
        }
    }

    override fun close() {
        job?.cancel()
        scanner.close()
    }
}