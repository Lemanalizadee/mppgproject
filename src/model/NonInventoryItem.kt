package model

class NonInventoryItem(
    itemName: String,
    itemNumber: Int,
    itemDescription: String
    // REMOVED: private var _location: String
) : Item(itemName, itemNumber, itemDescription) {

    // REMOVED: var location getter/setter

    override fun getDetails(): String {
        return """
            |Type: Non-Inventory Item
            |Name: $itemName
            |Number: $itemNumber
            |Description: $itemDescription
        """.trimMargin()
        // REMOVED: Location line
    }
}
