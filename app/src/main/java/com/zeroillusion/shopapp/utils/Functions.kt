package com.zeroillusion.shopapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
fun encodeBase64(fragment: Fragment, imageUri: Uri): String {
    val bitmapOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(fragment.requireContext().contentResolver, imageUri))
    } else {
        MediaStore.Images.Media.getBitmap(fragment.requireContext().contentResolver, imageUri)
    }
    val bitmap = if (bitmapOriginal.width >= bitmapOriginal.height){
        Bitmap.createBitmap(
            bitmapOriginal, bitmapOriginal.width /2 - bitmapOriginal.height /2, 0,
            bitmapOriginal.height, bitmapOriginal.height
        )
    }else{
        Bitmap.createBitmap(
            bitmapOriginal, 0, bitmapOriginal.height /2 - bitmapOriginal.width /2,
            bitmapOriginal.width, bitmapOriginal.width
        )
    }
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // bm is the bitmap object
    val bytes: ByteArray = stream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

fun decodeBase64(base64Encode: String): Bitmap {
    val bytes: ByteArray = Base64.decode(base64Encode, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}