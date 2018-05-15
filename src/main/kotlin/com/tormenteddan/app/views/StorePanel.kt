package com.tormenteddan.app.views

import com.tormenteddan.app.services.ADDRESS_LIME_DR
import com.tormenteddan.app.services.DataService
import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.util.Article
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import tornadofx.*


class StorePanel : Fragment() {
    override val scope = super.scope as StoreScope
    val store = scope.store

    private val type = if (store.address == ADDRESS_LIME_DR)
        "Sandwich" else "Torta"

    private val data = DataService
    private val menuGroup = ToggleGroup()
    override val root: BorderPane = borderpane {
        addClass("store-panel")
        top {
            borderpane {
                addClass("header-pane")
                center {
                    label(store.addressProperty) {
                        addClass("header-title")
                    }
                }
            }
        }
        center {
            flowpane {
                addClass("store-menu-items")
                hgap = 10.0
                vgap = 10.0
                children.addAll(store.menu.withIndex().map {
                    MenuItemButton(it.value).apply {
                        addClass("small-text")
                        toggleGroup = menuGroup
                        add(VBox().apply {
                            alignment = Pos.CENTER
                            add(Label("$type ${it.index + 1}"))
                            add(ImageView("/ingredients/$type.png"))
                        })
                        onHover {
                            scaleXProperty()
                                    .animate(if (isHover) 1.05 else 1.0, 100.millis)
                            scaleYProperty()
                                    .animate(if (isHover) 1.05 else 1.0, 100.millis)
                        }
                        onDoubleClick {
                            val scope = BuilderScope(store, this.sandwich)
                            replaceWith(find<SandwichBuilder>(scope), slideIn)
                            menuGroup.toggles.forEach {
                                it.isSelected = false
                            }
                        }
                    }
                })
            }
        }
        bottom {
            hbox {
                addClass("dashboard")
                alignment = Pos.TOP_CENTER
                paddingTop = -15
                maxHeight = 350.0
                vbox(10) {
                    addClass("dashboard-item")
                    alignment = Pos.BOTTOM_RIGHT
                    label(Bindings.format("$%.2f",
                            store.balanceProperty / 100.0)) {
                        addClass("total-balance")
                    }
                    label("Store's balance").addClass("big-label")
                    button("$type Builder") {
                        addClass("green-button")
                        useMaxWidth = true
                        action {
                            val menuItem = menuGroup.selectedToggle as? MenuItemButton
                            val sandwich = menuItem?.sandwich
                                    ?: store.menu[0]
                            val scope = BuilderScope(store, sandwich)
                            replaceWith(find<SandwichBuilder>(scope), slideIn)
                            menuGroup.toggles.forEach {
                                it.isSelected = false
                            }
                        }
                    }
                    button("Go Back") {
                        addClass("back-button")
                        useMaxWidth = true
                        action {
                            runLater {
                                replaceWith(WelcomeScreen::class, slideOut)
                            }
                        }
                    }
                }
                tableview(store.missingItemsProperty) {
                    addClass("dashboard-item")
                    alignment = Pos.TOP_CENTER
                    prefWidth = 600.0
                    column("Article", Article::articleDescriptionProperty)
                            .weightedWidth(2.0)
                    column("Cost", Article::cost).cellFormat {
                        text = String.format("$%.2f", it / 100.0)
                    }
                    column("Missing", Article::missingProperty).weightedWidth(1.0)
                    onUserSelect {
                        runLater { data.supervisor.replenish(it) }
                    }
                    smartResize()
                }.clipBorderRadius()
            }
        }
    }

    class MenuItemButton
    (val sandwich: Sandwich) : ToggleButton(){
        init {
            addClass("store-menu-item")
        }
    }
}
