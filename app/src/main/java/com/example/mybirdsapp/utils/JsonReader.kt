package com.example.mybirdsapp.utils

import android.content.Context
import com.example.mybirdsapp.models.Bird
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader

fun loadJsonFromAssets(context: Context, fileName: String): List<Bird> {
    val inputStream = context.assets.open(fileName)
    val reader = InputStreamReader(inputStream)
    val type = object : TypeToken<List<Bird>>() {}.type
    return Gson().fromJson(reader, type)
}
