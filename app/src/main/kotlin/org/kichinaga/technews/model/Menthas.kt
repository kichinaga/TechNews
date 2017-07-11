package org.kichinaga.technews.model

/**
 * Created by kichinaga on 2017/07/06.
 */


data class Menthas(val query: Query) {

    data class Query(val count: Int,
                     val created: String,
                     val results: Results) {

        data class Results(val item: List<Item>) {

            data class Item(val title: String = "",
                            val description: String = "",
                            val link: String = "",
                            val pubDate: String = "",
                            val thumbnail: Thumbnail = Thumbnail()) {

                data class Thumbnail(val url: String = "")
            }
        }
    }
}