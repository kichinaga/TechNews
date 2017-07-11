package org.kichinaga.technews.apicaller

import io.reactivex.Observable
import org.kichinaga.technews.model.Menthas
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by kichinaga on 2017/04/24.
 * https://query.yahooapis.com/v1/public/yql?q=select * from rss(2) where url = 'http://menthas.com/top/rss'&format=json
 *
 * ?q=
 */


interface MenthasCaller {
    @GET("v1/public/yql")
    fun getMenthasNews(@Query("q") query: String, @Query("format") format: String = "json"): Observable<Menthas>
}