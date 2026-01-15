package com.example.modular_drone_app.data.connection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class SocketClient(
    private val ip: String = "10.0.2.2",
    private val port: Int = 8080
) {
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    suspend fun connect(ip: String, port: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                disconnect()
                println("Connecting to $ip:$port...")
                socket = Socket(ip, port)
                writer = PrintWriter(socket!!.getOutputStream(), true)
                reader = BufferedReader(socket!!.getInputStream().reader())
                println("Connected!")
                true
            } catch (ex: Exception) {
                println("Connection error: ${ex.message}")
                false
            }
        }
    }

    suspend fun observeMessages(): Flow<String> = flow{
        while (socket != null && socket!!.isConnected && !socket!!.isClosed) {
            try {
                val line = reader?.readLine()
                if (line != null) {
                    emit(line)
                } else {
                    break
                }
            } catch (ex: Exception) {
                println("Read error: ${ex.message}")
                break
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun readLine(): String? {
        return withContext(Dispatchers.IO) {
            try {
                reader?.readLine()
            } catch (ex: Exception) {
                println("Read error: ${ex.message}")
                null
            }
        }
    }

    suspend fun sendLine(message: String) {
        return withContext(Dispatchers.IO) {
            try {
                writer?.println(message)
            } catch (ex: Exception) {
                println("Write error: ${ex.message}")
            }
        }
    }

    fun disconnect() {
        try {
            socket?.close()
            socket = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}