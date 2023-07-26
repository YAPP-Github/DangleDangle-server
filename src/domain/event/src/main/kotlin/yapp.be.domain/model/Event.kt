package yapp.be.domain.model

import yapp.be.model.enums.event.EventType
import java.io.Serializable

data class Event(
    val recordId: String,
    val json: String,
    val type: EventType,
) : Serializable {
    constructor(id: String, map: Map<String, String>) : this(
        id,
        map["json"]!!,
        EventType.valueOf(map["type"]!!),
    )
    fun toMap(): Map<ByteArray, ByteArray> =
        mapOf(
            "json".toByteArray() to this.json.toByteArray(),
            "type".toByteArray() to this.type.toString().toByteArray(),
        )
}
