package com.tormenteddan.util

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

/**
 * An inventory article should have a unique [id], an identifying [description]
 * and an associated [cost].
 *
 * It should also have a pair of associated quantities:
 *
 * 1. [current] quantity.
 * 2. [required] quantity.
 *
 * @property id Item's unique id.
 * @property origin Where the article is stored.
 * @property description Item's identifying description.
 * @property cost Item's associated cost. Its backing property is
 * [unsafeCost] and defaults to 0 if it's negative.
 * @property current The current quantity of this particular item inside an
 * inventory. Its backing property is [unsafeCurrent] and defaults to 0 if it's
 * negative.
 * @property required The required quantity of this particular item inside an
 * inventory. Its backing property is [unsafeRequired] and defaults to 0 if it's
 * negative.
 *
 * @param unsafeCost unchecked initial cost. May be negative.
 * @param unsafeCurrent unchecked initial quantity. May be negative.
 * @param unsafeRequired unchecked initial required quantity. May be negative.
 *
 *
 * @author daniel.aragon@ciencias.unam.mx
 */
data class Article(
        val id: Int,
        val origin: String,
        val description: String,
        var unsafeCost: Int,
        var unsafeCurrent: Int,
        var unsafeRequired: Int) {
    var cost: Int = if (unsafeCost < 0) 0 else unsafeCost
        get() = if (unsafeCost < 0) 0 else unsafeCost
        set(value) {
            field = if (value < 0) 0 else value
            unsafeCost = value
        }

    var required: Int = if (unsafeRequired < 0) 0 else unsafeRequired
        get() = if (unsafeRequired < 0) 0 else unsafeRequired
        set(value) {
            field = if (value < 0) 0 else value
            unsafeRequired = value
        }

    var current : Int by object: ObservableProperty<Int>(
            if(unsafeCurrent < 0) 0 else unsafeCurrent){
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return if (unsafeCurrent < 0) 0 else unsafeCurrent
        }

        override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) {
            super.afterChange(property, oldValue, newValue)
            missing = required - current
        }

        override fun beforeChange(property: KProperty<*>, oldValue: Int, newValue: Int): Boolean {
            unsafeCurrent = newValue
            return newValue > 0
        }
    }

    val missingProperty = SimpleIntegerProperty(required - current)
    var missing by missingProperty

    val articleDescriptionProperty = SimpleStringProperty(description)
    val articleDescription by articleDescriptionProperty

    val articleOriginProperty = SimpleStringProperty(origin)
    val articleOrigin by articleOriginProperty
}