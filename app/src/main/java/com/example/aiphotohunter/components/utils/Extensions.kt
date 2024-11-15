package com.example.aiphotohunter.components.utils

import android.graphics.Bitmap
import android.graphics.Canvas

fun Any?.isNotNull(): Boolean = (this != null)

fun getBitmapFromView(view: android.view.View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}