package model

class HazardousItem(
    itemName: String,
    itemNumber: Int,
    itemDescription: String,
    quantity: Int,
    location: String,
    val hazardLevel: HazardLevel
) : InventoryItem(itemName, itemNumber, itemDescription, quantity, location, ItemType.HAZARDOUS) {

    override fun getDetails(): String {
        return super.getDetails() + "\n⚠️  Hazard Level: $hazardLevel"
    }
}