package service

import model.*

class WarehouseManager : Warehouse {
    private val _inventoryItems = mutableMapOf<Int, InventoryItem>()
    private val _nonInventoryItems = mutableMapOf<Int, NonInventoryItem>()

    override fun addItem(item: Item): Boolean {
        // --- NEW REQUIREMENT: GLOBAL UNIQUE ID CHECK ---
        // Check if ID exists in EITHER list before doing anything else
        if (_inventoryItems.containsKey(item.itemNumber) || _nonInventoryItems.containsKey(item.itemNumber)) {
            println(" Error: Item number ${item.itemNumber} already exists in the warehouse (Global Check).")
            return false
        }

        return when (item) {
            is InventoryItem -> {
                _inventoryItems[item.itemNumber] = item
                println(" Inventory item '${item.itemName}' added successfully")
                true
            }
            is NonInventoryItem -> {
                _nonInventoryItems[item.itemNumber] = item
                println(" Non-inventory item '${item.itemName}' added successfully")
                true
            }
            else -> {
                println(" Error: Invalid item type")
                false
            }
        }
    }

    override fun removeItem(itemNumber: Int): Boolean {
        return when {
            _inventoryItems.containsKey(itemNumber) -> {
                val item = _inventoryItems.remove(itemNumber)
                println(" Item '${item?.itemName}' removed from inventory")
                true
            }
            _nonInventoryItems.containsKey(itemNumber) -> {
                val item = _nonInventoryItems.remove(itemNumber)
                println(" Item '${item?.itemName}' removed from non-inventory")
                true
            }
            else -> {
                println(" Error: Item #$itemNumber not found")
                false
            }
        }
    }

    override fun updateQuantity(itemNumber: Int, newQuantity: Int): Boolean {
        val item = _inventoryItems[itemNumber]
        return if (item != null) {
            item.quantity = newQuantity
            println(" Quantity updated successfully for '${item.itemName}'")
            true
        } else {
            // Note: Non-inventory items don't have quantity, so we don't check there
            println(" Error: Inventory item #$itemNumber not found")
            false
        }
    }

    fun searchItems(query: String): List<Item> {
        val results = mutableListOf<Item>()
        val lowerQuery = query.lowercase()

        // Search Inventory Items (includes location search)
        _inventoryItems.values.forEach { item ->
            if (item.itemName.lowercase().contains(lowerQuery) ||
                item.location.lowercase().contains(lowerQuery) ||
                item.itemNumber.toString().contains(query)) {
                results.add(item)
            }
        }

        // Search Non-Inventory Items (REMOVED location search)
        _nonInventoryItems.values.forEach { item ->
            if (item.itemName.lowercase().contains(lowerQuery) ||
                // item.location -> REMOVED because it no longer exists
                item.itemNumber.toString().contains(query)) {
                results.add(item)
            }
        }
        return results
    }

    fun generateReport() {
        println("\n" + "=".repeat(60))
        println("               WAREHOUSE REPORT")
        println("=".repeat(60))

        val inventoryCount = _inventoryItems.size
        val nonInventoryCount = _nonInventoryItems.size
        val totalQuantity = _inventoryItems.values.sumOf { it.quantity }

        println("\nSummary:")
        println("  Inventory Items: $inventoryCount")
        println("  Non-Inventory Items: $nonInventoryCount")
        println("  Total Quantity in Stock: $totalQuantity units")

        Item.printTotalItems()

        val locationBreakdown = mutableMapOf<String, Int>()
        _inventoryItems.values.forEach { item ->
            locationBreakdown[item.location] = (locationBreakdown[item.location] ?: 0) + item.quantity
        }

        if (locationBreakdown.isNotEmpty()) {
            println("\nInventory by Location:")
            locationBreakdown.forEach { (location, quantity) ->
                println("  $location: $quantity units")
            }
        }

        println("=".repeat(60) + "\n")
    }

    fun getItem(itemNumber: Int): Item? {
        return _inventoryItems[itemNumber] ?: _nonInventoryItems[itemNumber]
    }

    fun displayAllItems() {
        println("\n" + "=".repeat(60))
        println("               ALL WAREHOUSE ITEMS")
        println("=".repeat(60))

        if (_inventoryItems.isEmpty() && _nonInventoryItems.isEmpty()) {
            println("\nNo items in warehouse.")
        } else {
            if (_inventoryItems.isNotEmpty()) {
                println("\n--- INVENTORY ITEMS ---")
                _inventoryItems.values.forEachIndexed { index, item ->
                    println("\n[${index + 1}]")
                    println(item.getDetails())
                    println("-".repeat(40))
                }
            }
            if (_nonInventoryItems.isNotEmpty()) {
                println("\n--- NON-INVENTORY ITEMS ---")
                _nonInventoryItems.values.forEachIndexed { index, item ->
                    println("\n[${index + 1}]")
                    println(item.getDetails())
                    println("-".repeat(40))
                }
            }
        }
        println("=".repeat(60) + "\n")
    }
}