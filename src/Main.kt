import model.*
import service.WarehouseManager
import util.InvalidItemOperationException
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun main() {
    val warehouse = WarehouseManager()
    var running = true

    println("\n" + "=".repeat(60))
    println("          WAREHOUSE COUNTER APPLICATION")
    println("=".repeat(60))

    while (running) {
        try {
            println("\n--- MAIN MENU ---")
            println("1. Add Inventory Item (Standard)")
            println("2. Add Non-Inventory Item")
            println("3. Add Perishable Item")
            println("4. Add Hazardous Item")
            println("5. Remove Item")
            println("6. Update Quantity")
            println("7. Add Stock to Item")
            println("8. Remove Stock from Item")
            println("9. Search Items")
            println("10. Display All Items")
            println("11. Generate Report")
            println("12. View Item History")
            println("0. Exit")

            // Renamed function call
            val choice = inputInt("\nEnter your choice: ")

            when (choice) {
                1 -> {
                    println("\n--- Add Standard Inventory Item ---")
                    print("Item Name: ")
                    val name = readLine() ?: ""
                    // Renamed function call
                    val number = inputInt("Item Number: ")
                    print("Description: ")
                    val description = readLine() ?: ""
                    // Renamed function call
                    val quantity = inputInt("Quantity: ")
                    print("Location: ")
                    val location = readLine() ?: ""

                    val item = InventoryItem(name, number, description, quantity, location, ItemType.STANDARD)
                    warehouse.addItem(item)
                }
                2 -> {
                    println("\n--- Add Non-Inventory Item ---")
                    print("Item Name: ")
                    val name = readLine() ?: ""
                    val number = inputInt("Item Number: ")
                    print("Description: ")
                    val description = readLine() ?: ""

                    val item = NonInventoryItem(name, number, description)
                    warehouse.addItem(item)
                }
                3 -> {
                    println("\n--- Add Perishable Item ---")
                    print("Item Name: ")
                    val name = readLine() ?: ""
                    val number = inputInt("Item Number: ")
                    print("Description: ")
                    val description = readLine() ?: ""
                    val quantity = inputInt("Quantity: ")
                    print("Location: ")
                    val location = readLine() ?: ""

                    // Renamed function call
                    val expirationDate = inputDate()

                    val item = PerishableItem(name, number, description, quantity, location, expirationDate)
                    warehouse.addItem(item)
                }
                4 -> {
                    println("\n--- Add Hazardous Item ---")
                    print("Item Name: ")
                    val name = readLine() ?: ""
                    val number = inputInt("Item Number: ")
                    print("Description: ")
                    val description = readLine() ?: ""
                    val quantity = inputInt("Quantity: ")
                    print("Location: ")
                    val location = readLine() ?: ""

                    // Renamed function call
                    val hazardLevel = inputHazardLevel()

                    val item = HazardousItem(name, number, description, quantity, location, hazardLevel)
                    warehouse.addItem(item)
                }
                5 -> {
                    println("\n--- Remove Item ---")
                    val number = inputInt("Enter Item Number: ")
                    warehouse.removeItem(number)
                }
                6 -> {
                    println("\n--- Update Quantity ---")
                    val number = inputInt("Enter Item Number: ")
                    val newQuantity = inputInt("Enter New Quantity: ")
                    warehouse.updateQuantity(number, newQuantity)
                }
                7 -> {
                    println("\n--- Add Stock ---")
                    val number = inputInt("Enter Item Number: ")
                    val amount = inputInt("Enter Amount to Add: ")
                    val item = warehouse.getItem(number)
                    if (item is InventoryItem) {
                        item.addStock(amount)
                    } else {
                        println(" Error: Item not found or not an inventory item")
                    }
                }
                8 -> {
                    println("\n--- Remove Stock ---")
                    val number = inputInt("Enter Item Number: ")
                    val amount = inputInt("Enter Amount to Remove: ")
                    val item = warehouse.getItem(number)
                    if (item is InventoryItem) {
                        item.removeStock(amount)
                    } else {
                        println(" Error: Item not found or not an inventory item")
                    }
                }
                9 -> {
                    println("\n--- Search Items ---")
                    print("Enter search term (name, location, or item number): ")
                    val query = readLine() ?: ""
                    val results = warehouse.searchItems(query)
                    if (results.isEmpty()) {
                        println("\nNo items found matching '$query'")
                    } else {
                        println("\nSearch Results (${results.size} found):")
                        println("=".repeat(60))
                        results.forEachIndexed { index, item ->
                            println("\n[${index + 1}]")
                            println(item.getDetails())
                            println("-".repeat(40))
                        }
                    }
                }
                10 -> {
                    warehouse.displayAllItems()
                }
                11 -> {
                    warehouse.generateReport()
                }
                12 -> {
                    println("\n--- View Item History ---")
                    val number = inputInt("Enter Item Number: ")
                    val item = warehouse.getItem(number)
                    if (item is InventoryItem) {
                        item.showHistory()
                    } else {
                        println(" Error: Item not found or not an inventory item")
                    }
                }
                0 -> {
                    println("\nThank you for using Warehouse Counter Application!")
                    running = false
                }
                else -> {
                    println("\n Invalid choice. Please try again.")
                }
            }
        } catch (e: InvalidItemOperationException) {
            println("\n!!! OPERATION ERROR: ${e.message} !!!")
        } catch (e: Exception) {
            println("\n!!! UNEXPECTED ERROR: ${e.message} !!!")
            e.printStackTrace()
        }
    }
}

// --- HELPER FUNCTIONS (Renamed to avoid conflicts) ---

fun inputInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readLine()
        val number = input?.toIntOrNull()
        if (number != null && number >= 0) {
            return number
        }
        println(" Invalid input. Please enter a valid positive number.")
    }
}

fun inputHazardLevel(): HazardLevel {
    while (true) {
        print("Hazard Level (Low/Medium/High): ")
        val input = readLine()?.uppercase()?.trim() ?: ""
        try {
            return HazardLevel.valueOf(input)
        } catch (e: IllegalArgumentException) {
            println(" Invalid level. Please enter Low, Medium, or High.")
        }
    }
}

fun inputDate(): String {
    while (true) {
        print("Expiration Date (YYYY-MM-DD): ")
        val input = readLine() ?: ""
        try {
            LocalDate.parse(input)
            return input
        } catch (e: DateTimeParseException) {
            println(" Invalid date. Please use format YYYY-MM-DD (e.g., 2025-12-31).")
        }
    }
}