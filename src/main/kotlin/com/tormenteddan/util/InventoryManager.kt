package com.tormenteddan.util

/**
 * An inventory manager object (an object that has a collection of items with
 * associated quantities) must provide a way to modify the collection without
 * exposing the collection itself. It must at least implement a way to
 * [replenish] the inventory and [consume] from it.
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
interface InventoryManager {
    /**
     * Finds an [item] and subtracts an [amount] from its inventory if the
     * [amount] cannot be covered with the item's inventory, it's not modified.
     *
     * @param item The item.
     * @param amount The positive amount that should be removed from the item's
     * inventory.
     * @return true if the inventory changed as a result of this call, false
     * otherwise.
     */
    fun consume(item: Any, amount: Int = 1): Boolean

    /**
     * Finds an [item] and adds an [amount] to its inventory.
     *
     * @param item The item.
     * @param amount The positive amount that should be added to the item's
     * inventory.
     * @return true if the inventory changed as a result of this call, false
     * otherwise.
     */
    fun replenish(item: Any, amount: Int = 1): Boolean

    /**
     * Checks if an [item] is contained in the inventory and if it is, it
     * also checks if it has at least some [amount].
     *
     * @param item The item that we want to check for.
     * @param amount The amount of [items][item] we are checking for.
     * @return true if the inventory has enough of the [items][item].
     */
    fun contains(item: Any, amount: Int = 1): Boolean
}