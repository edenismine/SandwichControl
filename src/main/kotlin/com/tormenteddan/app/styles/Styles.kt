package com.tormenteddan.app.styles

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet(){
    companion object {
        val ingredientCell by cssclass()
    }
    init {
        ingredientCell and selected {
            label{
                textFill = Color.BLACK
            }
            s(".font-awesome"){
                textFill = c("#d92f3b")
            }
        }
        ingredientCell and focused {
            label{
                textFill = Color.BLACK
            }
            s(".font-awesome"){
                textFill = c("#d92f3b")
            }
        }
        ingredientCell and armed {
            label{
                textFill = c("#d92f3b")
            }
        }
        ingredientCell and pressed {
            label{
                textFill = c("#d92f3b")
            }
        }
    }
}