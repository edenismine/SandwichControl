package com.tormenteddan.app

import com.tormenteddan.app.services.ALL_INGREDIENTS
import com.tormenteddan.app.services.DataService
import com.tormenteddan.app.services.description
import com.tormenteddan.app.styles.Styles
import com.tormenteddan.app.views.WelcomeScreen
import tornadofx.*

class MyApp : App(WelcomeScreen::class, Styles::class){
    /* DONT LOOSE THE SERVICES! */
    val data = DataService
    init {
        importStylesheet("/styles/styles.css")
    }
}