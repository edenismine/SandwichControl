package com.tormenteddan.app.services

import com.tormenteddan.sandwiches.Sandwich
import com.tormenteddan.stores.SandwichStore
import com.tormenteddan.stores.SandwichStoreClerk
import com.tormenteddan.stores.SandwichStoreSupervisor
import com.tormenteddan.util.Article
import tornadofx.*
import java.util.*

object DataService {
    /* HEADER TITLE */
    var windowTitle = "Store Control"

    /* SUPERVISOR */
    val supervisor = object : SandwichStoreSupervisor() {
        override fun update(o: Observable?, arg: Any?) {
            super.update(o, arg)
            // printDashboard()
        }
    }

    /* STORE 01 */
    val store01 = object : SandwichStore(TORTERIA, ADDRESS_TESLA_BLVD,
            TORTERIA_MENU) {
        init {
            populateInventory()
        }

        override fun populateInventory(): Boolean {
            if (inventory.isEmpty()) {
                inventory.addAll(setOf(
                        Article(0, address, WHITE_CHEESE_TAG, WHITE_CHEESE_COST, 100, 100),
                        Article(1, address, SPANISH_CHEESE_TAG, SPANISH_CHEESE_COST, 100, 100),
                        Article(2, address, TOMATO_TAG, TOMATO_COST, 100, 100),
                        Article(3, address, LETTUCE_TAG, LETTUCE_COST, 100, 100),
                        Article(4, address, HAM_TAG, HAM_COST, 100, 100),
                        Article(5, address, CHICKEN_TAG, CHICKEN_COST, 100, 100),
                        Article(6, address, HEAD_CHEESE_TAG, HEAD_CHEESE_COST, 100, 100),
                        Article(7, address, BEEF_MILANESA_TAG, BEEF_MILANESA_COST, 100, 100),
                        Article(8, address, MAYONNAISE_TAG, MAYONNAISE_COST, 100, 100),
                        Article(9, address, MUSTARD_TAG, MUSTARD_COST, 100, 100),
                        Article(10, address, KETCHUP_TAG, KETCHUP_COST, 100, 100),
                        Article(11, address, PLAIN_BOLILLO_TAG, PLAIN_BOLILLO_COST, 100, 100),
                        Article(12, address, WHOLEGRAIN_BOLILLO_TAG,
                                WHOLEGRAIN_BOLILLO_COST, 100, 100)))
            }
            return inventory.isNotEmpty()
        }
    }

    /* STORE 02 */
    val store02 = object : SandwichStore(TORTERIA, ADDRESS_EIGHT_AV,
            TORTERIA_MENU) {
        init {
            populateInventory()
        }

        override fun populateInventory(): Boolean {
            if (inventory.isEmpty()) {
                val array = arrayOf(
                        Article(0, address, WHITE_CHEESE_TAG, WHITE_CHEESE_COST, 100, 100),
                        Article(1, address, SPANISH_CHEESE_TAG, SPANISH_CHEESE_COST, 100, 100),
                        Article(2, address, TOMATO_TAG, TOMATO_COST, 100, 100),
                        Article(3, address, LETTUCE_TAG, LETTUCE_COST, 100, 100),
                        Article(4, address, HAM_TAG, HAM_COST, 100, 100),
                        Article(5, address, CHICKEN_TAG, CHICKEN_COST, 100, 100),
                        Article(6, address, HEAD_CHEESE_TAG, HEAD_CHEESE_COST, 100, 100),
                        Article(7, address, BEEF_MILANESA_TAG, BEEF_MILANESA_COST, 100, 100),
                        Article(8, address, MAYONNAISE_TAG, MAYONNAISE_COST, 100, 100),
                        Article(9, address, MUSTARD_TAG, MUSTARD_COST, 100, 100),
                        Article(10, address, KETCHUP_TAG, KETCHUP_COST, 100, 100),
                        Article(11, address, PLAIN_BOLILLO_TAG, PLAIN_BOLILLO_COST, 100, 100),
                        Article(12, address, WHOLEGRAIN_BOLILLO_TAG, WHOLEGRAIN_BOLILLO_COST, 100, 100))
                for (article in array)
                    inventory.add(article)
            }
            return inventory.isNotEmpty()
        }
    }

    /* STORE 03 */
    val store03 = object : SandwichStore(TORTERIA, ADDRESS_MAIN_ST,
            TORTERIA_MENU) {
        init {
            populateInventory()
        }

        override fun populateInventory(): Boolean {
            if (inventory.isEmpty()) {
                inventory = listOf(
                        Article(0, address, WHITE_CHEESE_TAG, WHITE_CHEESE_COST, 100, 100),
                        Article(1, address, SPANISH_CHEESE_TAG, SPANISH_CHEESE_COST, 100, 100),
                        Article(2, address, TOMATO_TAG, TOMATO_COST, 100, 100),
                        Article(3, address, LETTUCE_TAG, LETTUCE_COST, 100, 100),
                        Article(4, address, HAM_TAG, HAM_COST, 100, 100),
                        Article(5, address, CHICKEN_TAG, CHICKEN_COST, 100, 100),
                        Article(6, address, HEAD_CHEESE_TAG, HEAD_CHEESE_COST, 100, 100),
                        Article(7, address, BEEF_MILANESA_TAG, BEEF_MILANESA_COST, 100, 100),
                        Article(8, address, MAYONNAISE_TAG, MAYONNAISE_COST, 100, 100),
                        Article(9, address, MUSTARD_TAG, MUSTARD_COST, 100, 100),
                        Article(10, address, KETCHUP_TAG, KETCHUP_COST, 100, 100),
                        Article(11, address, PLAIN_BOLILLO_TAG, PLAIN_BOLILLO_COST, 100, 100),
                        Article(12, address, WHOLEGRAIN_BOLILLO_TAG, WHOLEGRAIN_BOLILLO_COST, 100, 100))
                        .observable()
            }
            return inventory.isNotEmpty()
        }
    }
    /* STORE 04 */
    val store04 = object : SandwichStore(SANDWICHERIA, ADDRESS_LIME_DR,
            SANDWICHERIA_MENU) {
        init {
            populateInventory()
        }

        /**
         * Clerks of the Lime Drv Sandwichería must add a bread discount to every
         * ingredientsList they make.
         */
        override fun buildClerk(name: String): SandwichStoreClerk {
            return object : SandwichStoreClerk(this, name) {
                /**
                 * Adds the bread discount to a ingredientsList if it doesn't have it yet.
                 */
                override fun Sandwich.withDiscounts(): Sandwich {
                    return if (BREAD_DISCOUNT !in this)
                        this + BREAD_DISCOUNT else this
                }
            }
        }

        override fun populateInventory(): Boolean {
            if (inventory.isEmpty()) {
                inventory = arrayListOf(
                        Article(0, address, LETTUCE_TAG, LETTUCE_COST, 100, 100),
                        Article(1, address, HAM_TAG, HAM_COST, 100, 100),
                        Article(2, address, CHICKEN_TAG, CHICKEN_COST, 100, 100),
                        Article(3, address, HEAD_CHEESE_TAG, HEAD_CHEESE_COST, 100, 100),
                        Article(4, address, SAUSAGE_TAG, SAUSAGE_COST, 100, 100),
                        Article(5, address, TOMATO_TAG, TOMATO_COST, 100, 100),
                        Article(6, address, WHITE_CHEESE_TAG, WHITE_CHEESE_COST, 100, 100),
                        Article(7, address, MAYONNAISE_TAG, MAYONNAISE_COST, 100, 100),
                        Article(8, address, KETCHUP_TAG, KETCHUP_COST, 100, 100),
                        Article(9, address, PLAIN_BOLILLO_TAG, PLAIN_BOLILLO_COST, 100, 100),
                        Article(10, address, WHOLEGRAIN_BOLILLO_TAG,
                                WHOLEGRAIN_BOLILLO_COST, 100, 100),
                        Article(11, address, BREAD_DISCOUNT_TAG,
                                BREAD_DISCOUNT_COST,
                                2147483647, 2147483647)).observable()
            }
            return inventory.isNotEmpty()
        }
    }

    /* CLERK 01 */
    val clerk01 = store01.addNewClerk("John")

    /* CLERK 02 */
    val clerk02 = store02.addNewClerk("Jim")

    /* CLERK 03 */
    val clerk03 = store03.addNewClerk("Kim")

    /* CLERK 04 */
    val clerk04 = store04.addNewClerk("Jane")

    init {
        /* SUPERVISE STORES */
        store01.addObserver(supervisor)
        store02.addObserver(supervisor)
        store03.addObserver(supervisor)
        store04.addObserver(supervisor)
    }
}