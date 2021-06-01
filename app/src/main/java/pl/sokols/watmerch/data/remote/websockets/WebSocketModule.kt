package pl.sokols.watmerch.data.remote.websockets

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompCommand
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.*

open class WebSocketModule {

    private val URL = "wss://10.0.2.2:443/chat"
    private val TAG = "WebSocketModule"

    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private lateinit var stompClient: StompClient
    private lateinit var uuid: UUID

    @SuppressLint("CheckResult")
    fun provideWebSocket(listener: WebSocketListener) {
        uuid = UUID.randomUUID()
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, URL)
        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.i(TAG, "Stomp Connection Opened")
                LifecycleEvent.Type.ERROR -> Log.d(TAG, "Error ", lifecycleEvent.exception)
                LifecycleEvent.Type.CLOSED -> Log.w(TAG, "Stomp Connection Closed")
                LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.d(TAG, "Failed Server")
            }
        }
        if (!stompClient.isConnected) {
            stompClient.connect()
        }
        stompClient.topic("/queue/support-$uuid")
            .subscribe({ stompMessage ->
                listener.onSocketListener(
                    Gson().fromJson(
                        stompMessage.payload.toString(),
                        WebSocketMessageModel::class.java
                    )
                )
            }) { throwable ->
                Log.d(TAG, throwable.message.toString() + "")
            }

        mainHandler.postDelayed(tick, 25000)
    }

    private val tick = object : Runnable {
        override fun run() {
            sendMessage()
            mainHandler.postDelayed(this, 120000)
        }
    }

    private fun sendMessage() {
        stompClient.send(
            StompMessage(
                StompCommand.SEND,
                listOf(StompHeader(StompHeader.DESTINATION, "/chat/support")),
                Gson().toJson(WebSocketMessageModel(uuid, "User", "Connect with WebSocket!"))
            )
        ).subscribe()
    }
}