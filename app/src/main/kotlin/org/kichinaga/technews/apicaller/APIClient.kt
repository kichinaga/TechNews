package org.kichinaga.technews.apicaller

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.kichinaga.dinnerselector.api.UriAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by kichinaga on 2017/07/06.
 *
 * retrofit仕立てのニュースサイトAPIクライアントを作成する関数群
 */

private val YQL_BASE_URL: String = "https://query.yahooapis.com/"

// Menthas
fun getMenthasClient(): MenthasCaller {
    val builder: Retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                    .add(UriAdapter.FACTORY)
                    .build()))
            .baseUrl(YQL_BASE_URL)
            .build()

    return builder.create(MenthasCaller::class.java)
}
