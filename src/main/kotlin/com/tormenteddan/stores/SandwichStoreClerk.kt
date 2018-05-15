package com.tormenteddan.stores

import com.tormenteddan.sandwiches.BaseSandwich
import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import com.tormenteddan.util.Transaction
import com.tormenteddan.util.TransactionType
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*

/**
 * A clerk has to:
 *
 * - [sell sandwiches][sellSandwich].
 * - [help customers design their sandwiches][designSandwich].
 * - [can get the sandwiches from the store's menu][getSandwich].
 * - can actually [make][makeSandwich] using the [store]'s ingredients.

 * A clerk knows when or how to apply a discount to a
 * ingredientsList, and [notifies][Observable.notifyObservers] the [store] whenever it
 * makes a [sale][Transaction].
 *
 * @param name The clerk's storeName.
 * @param store The store where the clerk works.
 */
abstract class SandwichStoreClerk
(store: SandwichStore, name: String) : Observable() {
    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
    val storeProperty = SimpleObjectProperty<SandwichStore>(store)
    var store by storeProperty
    val isAvailableProperty = SimpleBooleanProperty(true)
    var isAvailable: Boolean by isAvailableProperty


    /**
     * Given a list of [ingredients], the clerk will:
     *
     * 1. [design the ingredientsList][designSandwich].
     * 2. [add appropriate discounts][withDiscounts].
     * 3. [make the ingredientsList][makeSandwich].
     * 4. [and sell it][sellSandwich].
     *
     * If the [store] doesn't have enough ingredients, then its
     * [inventory][SandwichStore.inventory] remains untouched by this call.
     * If the prepared ingredientsList is sold successfully, the clerk's
     * [observers][obs] will be notified.
     *
     * @param ingredients The list of ingredients of a ingredientsList.
     *
     * @return true if the [store's][store] [inventory][SandwichStore.inventory]
     * changed as a result of this call, false otherwise.
     */
    fun sellSandwich(ingredients: Collection<SandwichIngredient>): Boolean {
        isAvailable = false
        val candidate = designSandwich(ingredients) ?: return false
        val sandwich = candidate.withDiscounts()
        val prepared = makeSandwich(sandwich)
        if (prepared) {
            val transaction =
                    Transaction(TransactionType.EARNED, store.address,
                            sandwich.name, sandwich.price)
            setChanged()
            notifyObservers(transaction)
        }
        isAvailable = true
        return prepared
    }

    /**
     * Given a number [n], the clerk retrieves the [n]-th menu item
     * from their store's [menu][SandwichStore.menu].
     *
     * @param n The menu index of the desired ingredientsList.
     * @return The n-th menu item or null if no such item exists.
     */
    fun getSandwich(n: Int): Sandwich? {
        isAvailable = false
        val candidate = store.menu.elementAtOrNull(n) ?: return null
        isAvailable = true
        return candidate.withDiscounts()
    }

    /**
     * Given a list of [SandwichIngredient], tries to create a [Sandwich].
     * It take into account the applicable discounts.
     *
     * @param ingredients A list of ingredientsList ingredients that might make a
     * ingredientsList if put together (must have exactly one bread component).
     *
     * @return A ingredientsList if the [list][ingredients] is valid (has exactly one
     * bread component) or null otherwise.
     */
    fun designSandwich(ingredients: Collection<SandwichIngredient>):
            Sandwich? {
        isAvailable = false
        // Extract the bread and non bread components.
        val (bread, notBread) = ingredients.partition { it is Bread }
        // Make sure there's only one bread.
        if (bread.size != 1) return null
        // Make the base of the ingredientsList
        var sandwich: Sandwich = BaseSandwich(bread.first() as Bread)
        // Add all the extra ingredients to the ingredientsList.
        for (i in notBread.map { it as Ingredient }) sandwich += i
        isAvailable = true
        return sandwich.withDiscounts()
    }

    /**
     * Attempts to make a ingredientsList using the ingredients available to the the
     * [store].
     *
     * @param sandwich The ingredientsList that should be made.
     *
     * @return true if the [store's][store] [inventory][SandwichStore.inventory]
     * changed as a result of this call, false otherwise.
     */
    private fun makeSandwich(sandwich: Sandwich): Boolean {
        isAvailable = false
        // About to take the ingredients form the store, better keep track of
        // what we are using in case we don't use it in the end.
        val accumulator = arrayListOf<Pair<SandwichIngredient, Int>>()
        // If all the items were able to be consumed from the store, we are
        // basically finished!
        val prepared = sandwich.ingredients.groupingBy { it }.eachCount()
                .all { (item, amount) ->
                    val consumed = store.consume(item, amount)
                    if (consumed) accumulator.add(item to amount)
                    return@all consumed
                }
        // If the ingredientsList cannot be completed, we have to put the
        // ingredients back!
        if (!prepared) for ((i, n) in accumulator) store.replenish(i, n)
        isAvailable = true
        return prepared
    }

    /**
     * Adds the proper discount (if any is applicable) to a ingredientsList.
     * If not overridden, this method does nothing to the original ingredientsList.
     *
     * @return the possibly discounted ingredientsList.
     */
    protected open fun Sandwich.withDiscounts(): Sandwich {
        return this
    }


    /**
     * A clerk can only add its [store] to its observers list.
     *
     * @param o The clerk's store.
     */
    final override fun addObserver(o: Observer?) {
        if (o == null)
            throw NullPointerException()
        if (o == store) {
            super.addObserver(o)
        }
    }

    @Deprecated("This does nothing when called by a SandwichStoreClerk")
    final override fun deleteObserver(o: Observer?) {
    }

    @Deprecated("This does nothing when called by a SandwichStoreClerk")
    final override fun deleteObservers() {
    }
}
