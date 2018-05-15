package com.tormenteddan.sandwiches

import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import javafx.collections.ObservableList
import tornadofx.*

/**
 * A ingredientsList that has a certain kind of [bread] as a base, and a [custom]
 * storeName.
 *
 * Only [Sandwich] implementor with a constructor.
 *
 * @param bread The bread used for this ingredientsList.
 * @param custom A custom storeName for the ingredientsList.
 *
 * @see Sandwich
 * @see SandwichIngredient
 * @see DecoratedSandwich
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
class BaseSandwich
(private val bread: Bread, private val custom: String? = null) : Sandwich {
    override val name: String
        get() = custom ?: bread.name
    override val ingredients: ObservableList<SandwichIngredient>
        get() = listOf(bread).observable()
    override val base: Bread
        get() = bread
    override val cost: Int
        get() = bread.cost

    override operator fun invoke(bread: Bread): Sandwich = BaseSandwich(bread)

    override operator fun minus(ingredient: Ingredient): Sandwich = this
}