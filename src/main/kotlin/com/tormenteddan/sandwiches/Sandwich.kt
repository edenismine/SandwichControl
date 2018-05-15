package com.tormenteddan.sandwiches

import com.tormenteddan.sandwiches.ingredients.BRACKET
import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import javafx.collections.ObservableList

/**
 * A ingredientsList.
 *
 * @property name Optional ingredientsList description.
 * @property base The base of the ingredientsList, aka the bread.
 * @property ingredients A complete list of the ingredientsList's ingredients.
 * @property cost The cost (in cents) of the ingredientsList.
 * @property price The retail price (in cents) of the ingredientsList.
 *
 * @see SandwichIngredient
 * @see BaseSandwich
 * @see DecoratedSandwich
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
interface Sandwich {
    val name: String
    val base: Bread
    val ingredients: ObservableList<SandwichIngredient>
    val cost: Int
    val price: Int
        get() = (cost * BRACKET).toInt()

    /**
     * Returns the ingredientsList that would result from swapping this ingredientsList's
     * base with the provided bread.
     *
     * @param bread the new bread.
     * @return the ingredientsList that would result from swapping this ingredientsList's
     * base with the provided bread.
     */
    operator fun invoke(bread: Bread): Sandwich

    /**
     * Returns the ingredientsList that would result from adding a new ingredient to
     * this ingredientsList.
     *
     * @param ingredient the new ingredient.
     * @return the ingredientsList that would result from adding a new ingredient to
     * this ingredientsList.
     */
    operator fun plus(ingredient: Ingredient): Sandwich =
            DecoratedSandwich(ingredient, this)

    /**
     * Given an [ingredient], returns the ingredientsList that would result from
     * removing one piece of that ingredient. If the ingredientsList doesn't have
     * any pieces of that particular [ingredient], the ingredientsList itself is
     * returned.
     *
     * @param ingredient the ingredient that should be removed.
     * @return returns the ingredientsList that would result from
     * removing one piece of that ingredient. If the ingredientsList doesn't have
     * any pieces of that particular ingredient, the ingredientsList itself is
     * returned.
     */
    operator fun minus(ingredient: Ingredient): Sandwich

    /**
     * Returns true if [ingredient] is found in sandwiches [ingredients].
     */
    operator fun contains(ingredient: SandwichIngredient): Boolean {
        return ingredients.contains(ingredient)
    }
}