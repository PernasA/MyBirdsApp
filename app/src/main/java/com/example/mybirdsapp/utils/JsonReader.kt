package com.example.mybirdsapp.utils

import android.content.Context
import com.example.mybirdsapp.models.BirdJson
import com.example.mybirdsapp.models.ObservationRoute
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader

interface InterfaceJsonLoader {
    fun loadJsonObservationRoutesFromAssets(context: Context, fileName: String): List<ObservationRoute>
    fun loadJsonBirdsFromAssets(context: Context, fileName: String): List<BirdJson>
}

class JsonReader : InterfaceJsonLoader {
    override fun loadJsonObservationRoutesFromAssets(
        context: Context, fileName: String
    ): List<ObservationRoute> {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<ObservationRoute>>() {}.type
        return Gson().fromJson(reader, type)
    }

    override fun loadJsonBirdsFromAssets(
        context: Context, fileName: String
    ): List<BirdJson> {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<BirdJson>>() {}.type
        return Gson().fromJson(reader, type)
    }
}
