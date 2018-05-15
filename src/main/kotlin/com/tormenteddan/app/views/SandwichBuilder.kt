package com.tormenteddan.app.views

import com.tormenteddan.app.controllers.SandwichBuilderController
import com.tormenteddan.app.controllers.SandwichBuilderController.BUILD.*
import com.tormenteddan.app.services.ADDRESS_LIME_DR
import com.tormenteddan.app.services.BREAD_DISCOUNT
import com.tormenteddan.app.services.description
import com.tormenteddan.app.styles.Styles
import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import com.tormenteddan.stores.SandwichStore
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.TIMES_CIRCLE
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.text.TextAlignment
import tornadofx.*

class SandwichBuilder : Fragment() {
    override val scope = super.scope as BuilderScope

    private val storeProperty = scope.store.toProperty()
    val store: SandwichStore by storeProperty

    private val sandwichProperty = scope.sandwich.toProperty()
    var sandwich: Sandwich by sandwichProperty

    private val controller: SandwichBuilderController by inject()

    private val priceProperty = SimpleIntegerProperty(sandwich.price)
    private var price by priceProperty

    private var ingredientListProperty =
            SimpleListProperty<SandwichIngredient>(sandwich.ingredients)
    private var ingredientList by ingredientListProperty

    private val place = if (store.address == ADDRESS_LIME_DR) "Sandwich" else "Torta"

    init {
        title = "$place Builder"
    }

    override val root = borderpane {
        top {
            borderpane {
                addClass("header-pane")
                center {
                    label("$place Builder") {
                        addClass("header-title")
                    }
                }
            }
        }
        center {
            hbox {
                addClass("sandwich-builder-wrapper")
                hbox {
                    addClass("builder-input-wrapper")
                    vbox {
                        addClass("dashboard")
                        flowpane {
                            addClass("dashboard-item", "sandwich-builder-button-grid")
                            this.children.bind(controller
                                    .calculateIngredients(store)) {
                                button {
                                    when (it) {
                                        is Ingredient -> addClass("ingredient", "add-ingredient-button")
                                        is Bread -> addClass("bread", "add-ingredient-button")
                                    }
                                    tooltip(it.description())
                                    hbox {
                                        addClass("sandwich-ingredient-graphic")
                                        label(it.description())
                                        imageview("/ingredients/${it
                                                .description()}.png")
                                    }
                                    action {
                                        when (it) {
                                            is Ingredient ->
                                                sandwich += it
                                            is Bread ->
                                                sandwich = sandwich(it)
                                        }
                                        refreshList()
                                    }
                                }
                            }
                        }
                        gridpane {
                            addClass("dashboard-item")
                            alignment = Pos.CENTER
                            useMaxWidth = true
                            hgap = 10.0
                            vgap = 10.0
                            row {
                                alignment = Pos.CENTER_RIGHT
                                vbox(10) {
                                    label(Bindings.format("$%.2f", priceProperty
                                            / 100.0)) {
                                        addClass("total-balance")
                                        alignment = Pos.CENTER_RIGHT
                                        textAlignment = TextAlignment.RIGHT
                                    }
                                    // TODO dynamically add discount label
                                    label("Total") {
                                        addClass("big-label")
                                        alignment = Pos.CENTER_LEFT
                                        textAlignment = TextAlignment.LEFT
                                    }
                                }
                            }
                            row {
                                button("Go Back") {
                                    addClass("back-button")
                                    prefWidth = 150.0
                                    action {
                                        runLater {
                                            goBack()
                                        }
                                    }
                                }
                                button("Sell $place") {
                                    addClass("green-button")
                                    prefWidth = 150.0
                                    action {
                                        when (controller.sellSandwich(store, sandwich)) {
                                            SUCCESSFUL -> goBack()
                                            UNSUCCESSFUL -> warning("Sandwich couldn't be prepared", "The clerk couldn't make your sandwich, SORRY!")
                                            NO_CLERKS -> warning("No clerks available", "There are no clerks available. Please try in  a few minutes.")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                hbox {
                    addClass("builder-output-wrapper")
                    vbox {
                        addClass("dashboard")
                        listview(ingredientListProperty) {
                            addClass("sandwich-builder-ingredients",
                                    "dashboard-item")
                            cellFormat {
                                val value = it
                                addClass(Styles.ingredientCell)
                                onMouseClicked = EventHandler {
                                    if (value is Ingredient && value != BREAD_DISCOUNT) {
                                        sandwich -= value
                                        refreshList()
                                    }
                                }
                                graphic = hbox(10, Pos.CENTER) {
                                    prefWidth = 300.0
                                    addClass("sandwich-builder-ingredient")
                                    label(it.description())
                                    imageview("/ingredients/${it.description()}.png")
                                    if (it is Ingredient && it != BREAD_DISCOUNT) {
                                        add(FontAwesomeIconView(TIMES_CIRCLE)
                                                .visibleWhen { this.hoverProperty() }
                                                .apply {
                                                    fill = c("#d92f3b")
                                                    size = "20px"
                                                }
                                        )
                                    }
                                }
                            }
                        }.clipBorderRadius()
                        label("Ingredients") {
                            addClass("sandwich-builder-ingredients-header")
                            useMaxWidth = true
                        }
                    }
                }
            }
        }
    }

    private fun refreshList() {
        ingredientList = sandwich.ingredients.filter { it != null }
                .observable().sorted { o1, o2 ->
                    when (o2) {
                        is Bread -> when (o1) {
                            is Bread -> 0
                            is Ingredient -> 1
                        }
                        is Ingredient -> when (o1) {
                            is Bread -> -1
                            is Ingredient -> 0
                        }
                    }
                }
        price = sandwich.price
    }

    private fun goBack() {
        val scope = StoreScope(store)
        replaceWith(find<StorePanel>(scope), slideOut)
    }
}

