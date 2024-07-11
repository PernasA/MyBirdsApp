package com.example.mybirdsapp.utils

import android.content.Context
import com.example.mybirdsapp.models.BirdJson
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader

fun loadJsonFromAssets(context: Context, fileName: String): List<BirdJson> {
    val inputStream = context.assets.open(fileName)
    val reader = InputStreamReader(inputStream)
    val type = object : TypeToken<List<BirdJson>>() {}.type
    return Gson().fromJson(reader, type)
}
