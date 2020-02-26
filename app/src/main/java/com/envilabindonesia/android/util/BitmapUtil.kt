package com.envilabindonesia.android.util

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


open class BitmapUtil {

    companion object {

        fun getUri(bitmap: Bitmap?): Uri {

            var tempDir = Environment.getExternalStorageDirectory()
            tempDir = File(tempDir.absolutePath + "/.temp/")
            tempDir.mkdir()

            val tempFile = File.createTempFile(System.currentTimeMillis().toString(), ".jpg", tempDir)
            val bytes = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

            val bitmapData = bytes.toByteArray()
            val fos = FileOutputStream(tempFile)
            fos.write(bitmapData)
            fos.flush()
            fos.close()

            return Uri.fromFile(tempFile)

        }

    }

}