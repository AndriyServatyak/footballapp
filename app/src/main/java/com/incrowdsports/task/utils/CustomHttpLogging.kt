package com.incrowdsports.task.utils

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLogging : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        val logName = "OkHttp"
        if (!message.startsWith("{")) {
            Log.v(logName, message)
            return
        }
        try {
            val prettyPrintJson: String =
                GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
            Log.v(logName, prettyPrintJson)
        } catch (m: JsonSyntaxException) {
            Log.v(logName, message)

        }
    }
}