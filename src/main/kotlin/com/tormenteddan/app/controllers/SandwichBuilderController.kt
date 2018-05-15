package com.tormenteddan.app.controllers

import com.tormenteddan.app.services.ALL_INGREDIENTS
import com.tormenteddan.app.services.description
import com.tormenteddan.app.views.clipBorderRadius
import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import com.tormenteddan.stores.SandwichStore
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import tornadofx.*
import java.util.*

class SandwichBuilderController : Controller() {
    enum class BUILD {
        SUCCESSFUL,
        UNSUCCESSFUL,
        NO_CLERKS
    }

    fun sellSandwich(store: SandwichStore, sandwich: Sandwich): BUILD {
        return try {
            val success = store.clerks.first { it.isAvailable }
                    .sellSandwich(sandwich.ingredients)
            if (success) BUILD.SUCCESSFUL else BUILD.UNSUCCESSFUL
        } catch (e: NoSuchElementException) {
            BUILD.NO_CLERKS
        }
    }

    fun calculateIngredients(store: SandwichStore) :
            ObservableList<SandwichIngredient> {
        return store.inventory.map { article ->
            ALL_INGREDIENTS.find { it.description() == article.description }
        }.filter { it != null }.requireNoNulls().observable()
    }
}