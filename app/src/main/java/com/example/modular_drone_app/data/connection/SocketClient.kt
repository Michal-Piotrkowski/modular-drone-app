package com.example.modular_drone_app.data.connection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class SocketClient {
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    suspend fun connect(ip: String, port: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                println("DEBUG_NET: [SocketClient] Rozpoczynam łączenie z $ip:$port ...")
                disconnect() // Czyścimy stare śmieci

                socket = Socket()
                // Ustawiamy timeout 5 sekund, żeby nie czekać w nieskończoność
                val socketAddress = InetSocketAddress(ip, port)

                println("DEBUG_NET: [SocketClient] Fizyczne otwieranie gniazda...")
                socket?.connect(socketAddress, 5000)

                if (socket?.isConnected == true) {
                    println("DEBUG_NET: [SocketClient] Gniazdo otwarte! Tworzę strumienie IO...")
                    writer = PrintWriter(socket!!.getOutputStream(), true)
                    reader = BufferedReader(socket!!.getInputStream().reader())
                    println("DEBUG_NET: [SocketClient] SUKCES! Połączono i gotowe do pracy.")
                    true
                } else {
                    println("DEBUG_NET: [SocketClient] Gniazdo nie jest połączone po próbie connect().")
                    false
                }
            } catch (ex: Exception) {
                println("DEBUG_NET: [SocketClient] BŁĄD KRYTYCZNY podczas łączenia: ${ex.message}")
                ex.printStackTrace()
                false
            }
        }
    }

    fun observeMessages(): Flow<String> = flow{
        println("DEBUG_NET: [SocketClient] Zaczynam nasłuchiwać wiadomości w pętli...")
        while (socket != null && socket!!.isConnected && !socket!!.isClosed) {
            try {
                // To jest linijka blokująca - czeka aż przyjdą dane
                val line = reader?.readLine()

                if (line != null) {
                    // println("DEBUG_NET: [SocketClient] Odebrano surowe dane: $line") // Odkomentuj jeśli chcesz spam w logach
                    emit(line)
                } else {
                    println("DEBUG_NET: [SocketClient] Odebrano NULL (serwer zamknął połączenie).")
                    break
                }
            } catch (ex: Exception) {
                println("DEBUG_NET: [SocketClient] Błąd podczas czytania: ${ex.message}")
                break
            }
        }
        println("DEBUG_NET: [SocketClient] Koniec nasłuchiwania.")
    }.flowOn(Dispatchers.IO)

    suspend fun sendMessage(message: String) {
        withContext(Dispatchers.IO) {
            try {
                println("DEBUG_NET: [SocketClient] Wysyłam: $message")
                val outputStream = socket?.getOutputStream()
                if (outputStream != null) {
                    val bytes = (message + "\n").toByteArray(Charsets.UTF_8)
                    outputStream.write(bytes)
                    outputStream.flush()
                } else {
                    println("DEBUG_NET: [SocketClient] Błąd wysyłania - OutputStream jest null!")
                }
            } catch (e: Exception) {
                println("DEBUG_NET: [SocketClient] Błąd wysyłania: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        if (socket != null) {
            println("DEBUG_NET: [SocketClient] Zamykanie połączenia.")
            try {
                socket?.close()
                socket = null
                reader = null
                writer = null
            } catch (e: Exception) {
                println("DEBUG_NET: [SocketClient] Błąd przy zamykaniu: ${e.message}")
            }
        }
    }
}