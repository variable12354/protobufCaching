package com.example.protobuftask

import android.app.Application

class Controller :Application() {

    companion object {
        lateinit var instance: Controller private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}