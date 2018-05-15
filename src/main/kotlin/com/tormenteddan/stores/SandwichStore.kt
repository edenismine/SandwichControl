package com.tormenteddan.stores

import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import com.tormenteddan.util.Article
import com.tormenteddan.util.InventoryManager
import com.tormenteddan.util.Transaction
import com.tormenteddan.util.TransactionType
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.*
import java.util.*
import kotlin.properties.Delegates

/**
 * A ingredientsList store with a [storeName] located at an [address]. It can calculate
 * its [shopping list][missingItems] by checking its [inventory]. It notifies
 * its [observers][obs] about its transactions and its [balance].  Its
 * [clerks][clerks] help it:
 *
 * - [design][SandwichStoreClerk.designSandwich],
 * - [make][SandwichStoreClerk.getSandwich] and
 * - [sell sandwiches][SandwichStoreClerk.sellSandwich].
 *
 * It implements the [InventoryManager] interface which means that
 * administrators, employees or supervisors can also [consume] the store's
 * items or [replenish] them to keep its inventory full.
 *
 * A ingredientsList store is [Observable], and is usually observed by
 * [some supervisor][SandwichStoreSupervisor].
 *
 * @param name The store's description.
 * @param address The store's address.
 * @param menu The store's menu.
 *
 * @property storeName The store's description.
 * @property address The store's address.
 * @property inventory The store's inventory.
 * @property clerks The store's clerks.
 * @property missingItems The store's current shopping list.
 * @property balance The store's current balance.
 *
 * @see SandwichStoreSupervisor
 * @see SandwichStoreClerk
 * @see InventoryManager
 * @see Observable
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
abstract class SandwichStore
(name: String, address: String, menu: Collection<Sandwich>) : Observable(), Observer,
        InventoryManager {
    val menuProperty = SimpleObjectProperty(menu.toList().observable())
    var menu: ObservableList<Sandwich> by menuProperty
    val addressProperty = SimpleStringProperty(address)
    var address: String by addressProperty
    val storeNameProperty = SimpleStringProperty(name)
    var storeName: String by storeNameProperty

    var inventory: ObservableList<Article> by
    Delegates.vetoable(arrayListOf<Article>().observable()) { _, _, new ->
        new.groupBy { it.id }.map { (_, v) -> v.size }.all { it == 1 }
    }

    val clerks: ArrayList<SandwichStoreClerk> = arrayListOf()

    val balanceProperty = SimpleIntegerProperty(0)
    var balance by balanceProperty


    val missingItemsProperty = SimpleListProperty<Article>(
            arrayListOf<Article>().observable())
    var missingItems by missingItemsProperty

    /**
     * Populates the store's inventory.
     *
     * @return true if the inventory was successfully initialized.
     */
    protected abstract fun populateInventory(): Boolean

    /**
     * Creates a new clerk for the store.
     *
     * @param name the clerk's storeName.
     */
    protected open fun buildClerk(name: String): SandwichStoreClerk {
        return object : SandwichStoreClerk(this, name) {}
    }

    /**
     * [Builds][buildClerk] a new clerk, adds and adds it to the store's
     * [clerks].
     *
     * @param name The new clerk's storeName.
     * @return a new clerk with the given [name].
     */
    fun addNewClerk(name: String): SandwichStoreClerk {
        val new = buildClerk(name)
        new.addObserver(this)
        clerks += new
        return new
    }

    /**
     * Unsafe method that modifies the current quantity of a an [ingredient]
     * inside the store's [inventory]. It adds the given [amount] to the
     * item's entry but doesn't check if the amount is positive
     * or negative.
     *
     * @param ingredient The ingredient.
     * @param amount The quantity that should be added to the
     * [ingredient's][ingredient] current quantity inside the store's
     * [inventory].
     *
     * @return true if the store's inventory changed as result of this call.
     */
    private fun Collection<Article>.unsafeMod(
            ingredient: SandwichIngredient, amount: Int = 1): Boolean {
        val (description, cost) = when (ingredient) {
            is Bread -> ingredient.type to ingredient.cost
            is Ingredient -> ingredient.description to ingredient.cost
        }
        val candidate = this.find {
            it.description == description && it.cost == cost
        }
        if (candidate == null) {
            return false
        } else {
            candidate.current += amount
            return amount != 0
        }
    }

    final override fun consume(item: Any, amount: Int): Boolean {
        if (amount <= 0) return false

        if (item is SandwichIngredient) {
            val success = inventory.unsafeMod(item, -amount)
            if (success) {
                setChanged()
                notifyObservers()
            }
            return success
        } else if (item is Article) {
            val candidate = inventory.find { it == item }
            if (candidate == null) {
                return false
            } else if (candidate.current >= amount) {
                candidate.current -= amount
                setChanged()
                notifyObservers()
                return true
            }
        }
        return false
    }

    final override fun replenish(item: Any, amount: Int): Boolean {
        if (amount <= 0) return false
        when (item) {
            is SandwichIngredient -> {
                val success = inventory.unsafeMod(item, amount)
                if (success) {
                    setChanged()
                    notifyObservers()
                }
                return success
            }
            is Article -> {
                val candidate = inventory.find { it == item }
                        ?: return false
                candidate.current += amount
                setChanged()
                notifyObservers()
                return true
            }
            else -> return false
        }
    }

    final override fun contains(item: Any, amount: Int): Boolean {
        if (amount <= 0) return true
        val candidate = when (item) {
            is Bread -> inventory.find {
                it.description == item.type && it.cost == item.cost
            }
            is Ingredient -> inventory.find {
                it.description == item.description && it.cost == item.cost
            }
            is Article -> inventory.find { it == item }
            else -> null
        }
        candidate ?: return false
        return candidate.current >= amount
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an [Observable] object's
     * [notifyObservers][Observable.notifyObservers] method to have all the
     * object's observers notified of the change.
     *
     * @param o the observable object.
     * @param arg argument passed to the
     * [notifyObservers][Observable.notifyObservers] method.
     */
    final override fun update(o: Observable?, arg: Any?) {
        if (arg is Transaction)
            balance += when (arg.type) {
                TransactionType.EARNED -> arg.cents
                TransactionType.SPENT -> -arg.cents
            }
        setChanged()
        notifyObservers(arg)
    }

    /**
     * Creates a string representation of the store:
     *
     * ```kotlin
     * "$storeName located at $address"
     * ```
     *
     * @return A string representation of the store.
     */
    final override fun toString(): String {
        return "$storeName located at $address"
    }

    final override fun setChanged() {
        super.setChanged()
        missingItems = inventory.filtered { it.missing > 0 }
    }
}




