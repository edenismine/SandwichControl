package com.tormenteddan.sandwiches

import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import javafx.collections.ObservableList
import tornadofx.*

/**
 * A [sandwich] decorated with an [ingredient].
 *
 * @property sandwich the ingredientsList this decorator is decorating.
 * @property ingredient the description that's added on top of the [sandwich].
 *
 * @see SandwichIngredient
 * @see BaseSandwich
 * @see Sandwich
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
class DecoratedSandwich
(private val ingredient: Ingredient, private val sandwich: Sandwich) : Sandwich {

    override val name: String
        get() = sandwich.name
    override val ingredients: ObservableList<SandwichIngredient>
        get() = ArrayList(sandwich.ingredients).apply { add(ingredient) }.observable()
    override val cost: Int
        get() = sandwich.cost + ingredient.unsafeCost
    override val base: Bread
        get() = sandwich.base

    override operator fun invoke(bread: Bread): Sandwich {
        return DecoratedSandwich(ingredient, sandwich.invoke(bread))
    }

    override operator fun minus(ingredient: Ingredient): Sandwich {
        return if (ingredient == this.ingredient) {
            sandwich
        } else {
            DecoratedSandwich(this.ingredient, sandwich.minus(ingredient))
        }
    }
}