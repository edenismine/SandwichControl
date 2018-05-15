package com.tormenteddan.app.services

import com.tormenteddan.sandwiches.BaseSandwich
import com.tormenteddan.sandwiches.ingredients.Bread
import com.tormenteddan.sandwiches.ingredients.Ingredient
import com.tormenteddan.sandwiches.ingredients.SandwichIngredient
import com.tormenteddan.stores.SandwichStoreSupervisor

/* DEMO CONSTANTS */

/** Tag corresponding to a bread discount*/
const val BREAD_DISCOUNT_TAG = "Descuento de pan"

/** Cost (in cents) of a bread discount */
const val BREAD_DISCOUNT_COST = -50

/** Plain bread discount */
val BREAD_DISCOUNT = Ingredient(BREAD_DISCOUNT_TAG, BREAD_DISCOUNT_COST)

/* ============================ INGREDIENT TAGS ============================ */

/** Tag corresponding to white cheese*/
const val WHITE_CHEESE_TAG = "Queso blanco"
/** Tag corresponding to spanish cheese*/
const val SPANISH_CHEESE_TAG = "Queso manchego"
/** Tag corresponding to tomato*/
const val TOMATO_TAG = "Jitomate"
/** Tag corresponding to lettuce*/
const val LETTUCE_TAG = "Lechuga"
/** Tag corresponding to ham*/
const val HAM_TAG = "Jamón"
/** Tag corresponding to chicken*/
const val CHICKEN_TAG = "Pollo"
/** Tag corresponding to head cheese*/
const val HEAD_CHEESE_TAG = "Queso de puerco"
/** Tag corresponding to beef milanesa*/
const val BEEF_MILANESA_TAG = "Milanesa de res"
/** Tag corresponding to mayonnaise*/
const val MAYONNAISE_TAG = "Mayonesa"
/** Tag corresponding to mustard*/
const val MUSTARD_TAG = "Mostaza"
/** Tag corresponding to ketchup*/
const val KETCHUP_TAG = "Catsup"
/** Tag corresponding to plain bolillo*/
const val PLAIN_BOLILLO_TAG = "Bolillo blanco"
/** Tag corresponding to wholegrain bolillo*/
const val WHOLEGRAIN_BOLILLO_TAG = "Bolillo integral"
/** Tag corresponding to sausage*/
const val SAUSAGE_TAG = "Salchicha"


/* ============================ INGREDIENT COSTS ============================ */

/** Cost (in cents) of a portion of white cheese*/
const val WHITE_CHEESE_COST = 100
/** Cost (in cents) of a portion of spanish cheese*/
const val SPANISH_CHEESE_COST = 110
/** Cost (in cents) of a portion of tomato*/
const val TOMATO_COST = 40
/** Cost (in cents) of a portion of lettuce*/
const val LETTUCE_COST = 30
/** Cost (in cents) of a portion of ham*/
const val HAM_COST = 500
/** Cost (in cents) of a portion of chicken*/
const val CHICKEN_COST = 550
/** Cost (in cents) of a portion of head cheese*/
const val HEAD_CHEESE_COST = 450
/** Cost (in cents) of a portion of beef milanesa*/
const val BEEF_MILANESA_COST = 800
/** Cost (in cents) of a portion of mayonnaise*/
const val MAYONNAISE_COST = 30
/** Cost (in cents) of a portion of mustard*/
const val MUSTARD_COST = 30
/** Cost (in cents) of a portion of ketchup*/
const val KETCHUP_COST = 30
/** Cost (in cents) of a portion of plain bolillo*/
const val PLAIN_BOLILLO_COST = 100
/** Cost (in cents) of a portion of wholegrain bolillo*/
const val WHOLEGRAIN_BOLILLO_COST = 150
/** Cost (in cents) of a portion of sausage */
const val SAUSAGE_COST = 450

/* ============================= SANDWICH NAMES ============================= */

/** The storeName of a torta with plain bolillo*/
const val PLAIN_BOLILLO_NAME = "Torta simple"
/** The storeName of a torta with wholegrain bolillo*/
const val WHOLEGRAIN_BOLILLO_NAME = "Torta integral"

/* ============================== INGREDIENTS ============================== */

/** White cheese. */
val WHITE_CHEESE = Ingredient(WHITE_CHEESE_TAG, WHITE_CHEESE_COST)
/** Spanish cheese. */
val SPANISH_CHEESE = Ingredient(SPANISH_CHEESE_TAG, SPANISH_CHEESE_COST)
/** Tomato. */
val TOMATO = Ingredient(TOMATO_TAG, TOMATO_COST)
/** Lettuce. */
val LETTUCE = Ingredient(LETTUCE_TAG, LETTUCE_COST)
/** Ham. */
val HAM = Ingredient(HAM_TAG, HAM_COST)
/** Chicken. */
val CHICKEN = Ingredient(CHICKEN_TAG, CHICKEN_COST)
/** Head cheese. */
val HEAD_CHEESE = Ingredient(HEAD_CHEESE_TAG, HEAD_CHEESE_COST)
/** Beef milanesa. */
val BEEF_MILANESA = Ingredient(BEEF_MILANESA_TAG, BEEF_MILANESA_COST)
/** Mayonnaise. */
val MAYONNAISE = Ingredient(MAYONNAISE_TAG, MAYONNAISE_COST)
/** Mustard. */
val MUSTARD = Ingredient(MUSTARD_TAG, MUSTARD_COST)
/** Ketchup. */
val KETCHUP = Ingredient(KETCHUP_TAG, KETCHUP_COST)
/** Plain bolillo. */
val PLAIN_BOLILLO = Bread(PLAIN_BOLILLO_TAG, PLAIN_BOLILLO_COST,
        PLAIN_BOLILLO_NAME)
/** Wholegrain bolillo. */
val WHOLEGRAIN_BOLILLO = Bread(WHOLEGRAIN_BOLILLO_TAG,
        WHOLEGRAIN_BOLILLO_COST, WHOLEGRAIN_BOLILLO_NAME)
/** Sausage */
val SAUSAGE = Ingredient(SAUSAGE_TAG, SAUSAGE_COST)

/* ============================ ALL INGREDIENTS ============================ */

val ALL_INGREDIENTS = listOf(WHITE_CHEESE, SPANISH_CHEESE, TOMATO, LETTUCE,
        HAM, CHICKEN, HEAD_CHEESE, BEEF_MILANESA, MAYONNAISE, MUSTARD, KETCHUP,
        PLAIN_BOLILLO, WHOLEGRAIN_BOLILLO, SAUSAGE)


/* ============================= DEMO CONSTANTS ============================= */

/** Name of the torterías restaurant chain */
const val TORTERIA = "Juanito's Tortería"

/** Address of Main St Tortería */
const val ADDRESS_MAIN_ST = "Main St 1031"

/** Address of Tesla Av Tortería */
const val ADDRESS_EIGHT_AV = "Eight Av 1239"

/** Address of Tesla Av Tortería */
const val ADDRESS_TESLA_BLVD = "Tesla Blvd 72"

/** Name of the sandwicherías restaurant chain */
const val SANDWICHERIA = "Juanito's Sandwichería"

/** Address of Lime Drv Sandwichería */
const val ADDRESS_LIME_DR = "Lime Drv 42"

/**
 * Simplest torta template.
 */
val TORTA = BaseSandwich(PLAIN_BOLILLO, PLAIN_BOLILLO_NAME) +
        LETTUCE + TOMATO + MAYONNAISE + MUSTARD + KETCHUP

/**
 * The torterías' menu.
 */
val TORTERIA_MENU = listOf(
        TORTA + WHITE_CHEESE,
        TORTA + SPANISH_CHEESE,
        TORTA + HAM,
        TORTA + CHICKEN,
        TORTA + HEAD_CHEESE,
        TORTA + BEEF_MILANESA,
        TORTA + HAM + WHITE_CHEESE,
        TORTA + CHICKEN + WHITE_CHEESE,
        TORTA + HEAD_CHEESE + WHITE_CHEESE,
        TORTA + BEEF_MILANESA + WHITE_CHEESE,
        TORTA + HAM + SPANISH_CHEESE,
        TORTA + CHICKEN + SPANISH_CHEESE,
        TORTA + HEAD_CHEESE + SPANISH_CHEESE,
        TORTA + BEEF_MILANESA + SPANISH_CHEESE
)

/**
 * Simplest ingredientsList template.
 */
val SANDWICH = BaseSandwich(PLAIN_BOLILLO, PLAIN_BOLILLO_NAME) +
        LETTUCE + TOMATO + MAYONNAISE + KETCHUP
/**
 * The sandwicherías' menu.
 */
val SANDWICHERIA_MENU = listOf(
        SANDWICH + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + WHITE_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + HAM + BREAD_DISCOUNT,
        SANDWICH + CHICKEN + BREAD_DISCOUNT,
        SANDWICH + HEAD_CHEESE + BREAD_DISCOUNT,
        SANDWICH + SAUSAGE + BREAD_DISCOUNT,
        SANDWICH + HAM + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + CHICKEN + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + HEAD_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + SAUSAGE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + HAM + WHITE_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + CHICKEN + WHITE_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + HEAD_CHEESE + WHITE_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT,
        SANDWICH + SAUSAGE + WHITE_CHEESE + WHITE_CHEESE + BREAD_DISCOUNT
)

/* ========================== EXTENSION FUNCTIONS ========================== */

/**
 * Extension function that exhaustively attempts to retrieve a description
 * from a [SandwichIngredient] object.
 */
fun SandwichIngredient.description(): String = when (this) {
    is Bread -> this.type
    is Ingredient -> this.description
}

/**
 * Extension function that exhaustively attempts to retrieve a cost
 * from a [SandwichIngredient] object.
 */
fun SandwichIngredient.cost(): Int = when (this) {
    is Bread -> this.cost
    is Ingredient -> this.cost
}

/**
 * This function prints a dashboard that displays the supervisor's information.
 */
fun SandwichStoreSupervisor.printDashboard() {
    val width = 79
    println("|".padEnd(width, '=') + "|")
    println("|" +
            "DASHBOARD"
                    .padStart((width / 2) + 4)
                    .padEnd(width - 1) + "|")
    println("|".padEnd(width, '=') + "|")

    val globalBalance = balanceMap.values.sum()

    println("|  Balance: ${globalBalance / 100.0}".padEnd(width) + "|")
    for ((store, balance) in balanceMap) {
        println("|    $store : ${balance / 100.0}".padEnd(width) + "|")
    }

    println("|  Shopping lists:".padEnd(width) + "|")
    for (store in stores) {
        println("|    ${store.address}:".padEnd(width) + "|")
        store.missingItems.filter { it.missing > 0 }.forEach {
            println(("|      Missing ${it.missing} of " +
                    it.description).padEnd(width) + "|")
        }
    }

    println("|  Transactions:".padEnd(width) + "|")
    for ((type, origin, concept, cents) in ledger) {
        println("|    $type @$origin for $concept ${cents / 100.0}"
                .padEnd(width) + "|")
    }

    println("|".padEnd(width, '=') + "|")
}
