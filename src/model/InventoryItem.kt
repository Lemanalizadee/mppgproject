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
    itemName: String,
    itemNumber: Int,
    itemDescription: String,
    private var _quantity: Int,
    private var _location: String,
    private var _itemType: ItemType = ItemType.STANDARD
) : Item(itemName, itemNumber, itemDescription) {

    private val _history = mutableListOf<LogEntry>()

    var quantity: Int
        get() = _quantity
        set(value) {
            // Error Handling: Throw custom exception from util package
            if (value < 0) throw InvalidItemOperationException("Quantity cannot be negative")

            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val log = LogEntry(date, "Quantity changed from $_quantity to $value", "UPDATE")
            _history.add(log)
            _quantity = value
        }

    var location: String
        get() = _location
        set(value) { _location = value }

    var itemType: ItemType
        get() = _itemType
        set(value) { _itemType = value }

    override fun getDetails(): String {
        return """
            |Type: Inventory Item
            |Name: $itemName
            |Number: $itemNumber
            |Description: $itemDescription
            |Quantity: $quantity
            |Location: $location
            |Item Type: $itemType
        """.trimMargin()
    }

    fun addStock(amount: Int) {
        if (amount <= 0) {
            println("✗ Amount must be positive.")
            return
        }
        quantity = _quantity + amount
        println("✓ Added $amount units. New quantity: $quantity")
    }

    fun removeStock(amount: Int): Boolean {
        return if (_quantity >= amount) {
            quantity = _quantity - amount
            println("✓ Removed $amount units. New quantity: $quantity")
            true
        } else {
            println("✗ Insufficient stock! Available: $quantity, Requested: $amount")
            false
        }
    }

    fun showHistory() {
        if (_history.isEmpty()) {
            println("No history available for this item.")
        } else {
            println("\n--- History for $itemName ---")
            _history.forEach { log ->
                println("[${log.timestamp}] [${log.type}] ${log.message}")
            }
        }
    }
}