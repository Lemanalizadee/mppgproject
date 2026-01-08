package service

import model.Item

interface Warehouse {
    fun addItem(item: Item): Boolean
    fun removeItem(itemNumber: Int): Boolean
    fun updateQuantity(itemNumber: Int, newQuantity: Int): Boolean
}