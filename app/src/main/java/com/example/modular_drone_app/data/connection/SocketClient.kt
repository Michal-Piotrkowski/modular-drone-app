package com.example.modular_drone_app.data.connection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class SocketClient(
    private val ip: String = "192.168.1.4",
    private val port: Int = 8080
) {
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    suspend fun connect() {
        withContext(Dispatchers.IO) {
            try {
                socket = Socket(ip, port)
                writer = PrintWriter(socket!!.getOutputStream(), true)
                reader = BufferedReader(socket!!.getInputStream().reader())
                println("Connected to $ip:$port")
            }catch (ex: Exception){
                println("Connection error: ${ex.message}")
            }
        }
    }

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
}