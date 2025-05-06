package de.tho.cmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.tho.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WhatToDo",
    ) {
        App()
    }
}