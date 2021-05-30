package pl.sokols.watmerch.data.remote.websockets

interface WebSocketListener {
    fun onSocketListener(webSocketMessageModel: WebSocketMessageModel)
}