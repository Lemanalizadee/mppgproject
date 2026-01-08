package model

abstract class Item(
    private var _itemName: String,
    private var _itemNumber: Int,
    private var _itemDescription: String
) {

    companion object {
        var totalItemsCreated = 0
        fun printTotalItems() {
            println(" Global Statistic: Total Items Created since startup: $totalItemsCreated")
        }
    }

    init {
        totalItemsCreated++
    }

    var itemName: String
        get() = _itemName
        set(value) { _itemName = value }

    var itemNumber: Int
        get() = _itemNumber
        set(value) { _itemNumber = value }

    var itemDescription: String
        get() = _itemDescription
        set(value) { _itemDescription = value }

    abstract fun getDetails(): String
}