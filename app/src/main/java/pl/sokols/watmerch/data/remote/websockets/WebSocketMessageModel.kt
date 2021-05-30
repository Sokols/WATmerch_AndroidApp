package pl.sokols.watmerch.data.remote.websockets

import java.util.*

data class WebSocketMessageModel(
    var senderId: UUID? = null,
    var name: String? = null,
    var message: String? = null
)
