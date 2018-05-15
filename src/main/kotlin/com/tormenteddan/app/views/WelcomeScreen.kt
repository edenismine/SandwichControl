package com.tormenteddan.app.views

import com.tormenteddan.app.services.DataService.store01
import com.tormenteddan.app.services.DataService.store02
import com.tormenteddan.app.services.DataService.store03
import com.tormenteddan.app.services.DataService.store04
import com.tormenteddan.app.services.DataService.supervisor
import com.tormenteddan.util.Article
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import tornadofx.*

class WelcomeScreen : View("Store Control") {
    private val store01Scope = StoreScope(store01)
    private val store02Scope = StoreScope(store02)
    private val store03Scope = StoreScope(store03)
    private val store04Scope = StoreScope(store04)
    private lateinit var buttonBar: HBox
    override val root: BorderPane = borderpane {
        addClass("welcome-screen")
        top {
            borderpane {
                addClass("header-pane")
                center {
                    label("Store Control") {
                        addClass("header-title")
                    }
                }
            }
        }
        center {
            buttonBar = hbox {
                addClass("restaurant-button-bar")
                button(store01.addressProperty) {
                    id = "button01"
                    action { button01Pressed() }
                }
                button(store01.addressProperty) {
                    id = "button02"
                    action { button02Pressed() }
                }
                button(store01.addressProperty) {
                    id = "button03"
                    action { button03Pressed() }
                }
                button(store01.addressProperty) {
                    id = "button04"
                    action { button04Pressed() }
                }
            }
        }
        bottom {
            hbox {
                alignment = Pos.TOP_CENTER
                paddingTop = -15
                addClass("dashboard")
                vbox(10) {
                    addClass("dashboard-item")
                    style{
                        alignment = Pos.BOTTOM_RIGHT
                    }

                    label(Bindings.format("$%.2f",
                            store01.balanceProperty / 100.0)) {
                        addClass("store-balance")
                    }
                    label(store01.address).addClass("store-address")

                    label(Bindings.format("$%.2f",
                            store02.balanceProperty / 100.0)) {
                        addClass("store-balance")
                    }
                    label(store02.address).addClass("store-address")

                    label(Bindings.format("$%.2f",
                            store03.balanceProperty / 100.0)) {
                        addClass("store-balance")
                    }
                    label(store03.address).addClass("store-address")

                    label(Bindings.format("$%.2f",
                            store04.balanceProperty / 100.0))
                            .addClass("store-balance")
                    label(store04.address).addClass("store-address")

                    label(Bindings.format("$%.2f",
                            supervisor.totalBalanceProperty / 100.0))
                            .addClass("total-balance")
                    label("Total balance").addClass("big-label")
                    button("Replenish all") {
                        addClass("green-button")
                        useMaxWidth = true
                        action { replenishButtonPressed() }
                    }
                }
                tableview(supervisor.shoppingListProperty) {
                    addClass("dashboard-item")
                    alignment = Pos.TOP_CENTER
                    prefWidth = 600.0
                    column("Location", Article::articleOriginProperty).weightedWidth(2.0)
                    column("Article", Article::articleDescriptionProperty)
                            .weightedWidth(2.0)
                    column("Cost", Article::cost).weightedWidth(1.0)
                            .cellFormat {
                                text = String.format("$%.2f", it / 100.0)
                            }
                    column("Missing", Article::missing).weightedWidth(1.0)
                    onUserSelect {
                        runLater { supervisor.replenish(it) }
                    }
                    smartResize()
                }.clipBorderRadius()
            }.clipBorderRadius()
        }
        fitToParentSize()
    }

    private fun button01Pressed() {
        replaceWith(find<StorePanel>(store01Scope), slideIn)
    }

    private fun button02Pressed() {
        replaceWith(find<StorePanel>(store02Scope), slideIn)
    }

    private fun button03Pressed() {
        replaceWith(find<StorePanel>(store03Scope), slideIn)
    }

    private fun button04Pressed() {
        replaceWith(find<StorePanel>(store04Scope), slideIn)
    }

    private fun replenishButtonPressed() {
        supervisor.buyMissingItems()
    }

    override fun onDock() {
        super.onDock()
        currentStage?.isResizable = false

        /* ANIMATE BUTTONS */
        buttonBar.lookupAll(".button").forEach {
            with(it) {
                onHover {
                    scaleXProperty()
                            .animate(if (isHover) 1.05 else 1.0, 100.millis)
                    scaleYProperty()
                            .animate(if (isHover) 1.05 else 1.0, 100.millis)
                }
            }
        }
    }
}
