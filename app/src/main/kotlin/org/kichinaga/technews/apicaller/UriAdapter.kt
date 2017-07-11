package org.kichinaga.dinnerselector.api

import android.net.Uri
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by kichinaga on 2017/06/20.
 */

class UriAdapter : JsonAdapter<Uri>() {

    companion object {
        // Moshiのソースコードではアダプタ毎にファクトリを用意していたのでそれに習う。
        // 多分、この方法が手っ取り早いと思われます。
        // Uriなどのクラスごとに一つのアダプタを作成しなくてもOK。いくつかまとめてもOK
        val FACTORY: Factory = object: Factory {
            override fun create(type: Type, annotations: Set<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
                if (type === Uri::class.java) {
                    return UriAdapter()
                }
                return null
            }
        }
    }

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Uri {
        return Uri.parse(reader.nextString())
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Uri?) {
        writer.value(value.toString())
    }

    override fun toString(): String {
        return "JsonAdapter(Uri)"
    }
}