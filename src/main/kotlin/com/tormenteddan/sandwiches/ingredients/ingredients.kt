package com.tormenteddan.sandwiches.ingredients

/* =========================== INGREDIENT CLASSES =========================== */

/**
 * Sealed class. Extended by [Ingredient] and [Bread].
 */
sealed class SandwichIngredient

/**
 * Ingredient with [description] and [cost].
 *
 * @property description The ingredient's description.
 * @property cost The ingredients's cost (in cents).
 * @property unsafeCost The possibly negative ingredient's cost, a discount
 * has a negative unsafe cost but a cost of 0.
 */
data class Ingredient
(val description: String, var unsafeCost: Int) : SandwichIngredient() {
    var cost = if (unsafeCost < 0) 0 else unsafeCost
        get() = if (unsafeCost < 0) 0 else unsafeCost
        set(value) {
            field = if (value < 0) 0 else value
            unsafeCost = value
        }
}

/**
 * Bread [type] with [cost]. A ingredientsList will be named according to the type
 * of bread that's used.
 *
 * @property type Type of bread, e.g. plain white bread.
 * @property cost The cost (in cents) of this type of bread.
 * @property name The storeName of a ingredientsList that uses this bread.
 */
class Bread
(val type: String, cost: Int, val name: String) : SandwichIngredient() {
    var cost = if (cost < 0) 0 else cost
        set(value) {
            field = if (value < 0) 0 else value
        }

    /** Returns true if both objects match in all fields. */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Bread) return false
        return type == other.type &&
                cost == other.cost &&
                name == other.name
    }

    /** Returns a hash code value for the object. */
    override fun hashCode(): Int {
        val internal = type.hashCode() + cost.hashCode() + name.hashCode()
        return internal xor javaClass.hashCode()
    }

    /** Returns a string representation of the object. */
    override fun toString(): String {
        return "Bread(type=$type, cost=$cost, storeName=$name)"
    }
}


/* =============================== CONSTANTS =============================== */

/** Profit bracket multiplier */
const val BRACKET = 100 / 20.0