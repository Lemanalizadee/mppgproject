package model

class PerishableItem(
    itemName: String,
    itemNumber: Int,
    itemDescription: String,
    quantity: Int,
    location: String,
    val expirationDate: String
) : InventoryItem(itemName, itemNumber, itemDescription, quantity, location, ItemType.PERISHABLE) {

    override fun getDetails(): String {
        return super.getDetails() + "\nExpiration Date: $expirationDate"
    }
}