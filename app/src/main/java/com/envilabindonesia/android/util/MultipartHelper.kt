package com.envilabindonesia.android.util

/**
 * Created by galihadityo on 2019-04-11.
 */

import android.content.Context
import com.envilabindonesia.android.data.request.ItemAttachment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

open class MultipartHelper {

    companion object {

        fun createPartFromFile(file: File): RequestBody {
            return RequestBody.create(MediaType.parse("*/*"), file)
        }

        fun createPartFromData(context: Context?, itemAttachment: ItemAttachment?): MultipartBody.Part {
            val uri = itemAttachment?.uri
            val bitmap = itemAttachment?.bitmap

            val file = if (bitmap == null && uri != null) {
                // pdf, msword, image
                File(PathUtil.getPath(context, uri))
            } else {
                // camera
                File(PathUtil.getPath(context, BitmapUtil.getUri(bitmap)))
            }

            return MultipartBody.Part.createFormData(
                "file",
                file.name,
                createPartFromFile(file))
        }

        fun createListPartFromFile(list: ArrayList<ItemAttachment>): Array<MultipartBody.Part?> {
            val listParts = arrayOfNulls<MultipartBody.Part>(list.size)

            for ((a, i) in list.withIndex()) {
                val uri = i.uri
                val bitmap = i.bitmap

                val file = if (bitmap == null && uri != null) {
                    // pdf, msword, image
                    File(uri.path)
                } else {
                    // camera
                    File(BitmapUtil.getUri(bitmap).path)
                }

                listParts[a] = MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    createPartFromFile(file))
            }

            return listParts
        }

    }

}