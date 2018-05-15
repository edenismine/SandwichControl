package com.tormenteddan.app.views

import javafx.scene.layout.Region
import javafx.scene.shape.Rectangle
import tornadofx.*

val slideIn = ViewTransition.Slide(500.millis)
val slideOut = ViewTransition.Slide(500.millis,ViewTransition.Direction.RIGHT)

fun <T> T.clipBorderRadius(arcHeight: Double = 20.0, arcWeight: Double =20.0): T {
    if(this is Region){
        this.addClass("clipped")
        val rectangle = Rectangle()
        rectangle.widthProperty().bind(widthProperty())
        rectangle.heightProperty().bind(heightProperty())
        rectangle.arcHeight = arcHeight
        rectangle.arcWidth = arcWeight
        clip = rectangle
    }
    return this
}