package model

import util.InvalidItemOperationException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class LogEntry(
    val timestamp: String,
    val message: String,
    val type: String
)

open class InventoryItem(

}