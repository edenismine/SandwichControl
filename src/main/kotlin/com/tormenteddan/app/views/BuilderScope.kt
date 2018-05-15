package com.tormenteddan.app.views

import com.tormenteddan.stores.SandwichStore
import com.tormenteddan.sandwiches.Sandwich
import tornadofx.*

class BuilderScope(val store: SandwichStore, var sandwich: Sandwich) : Scope()