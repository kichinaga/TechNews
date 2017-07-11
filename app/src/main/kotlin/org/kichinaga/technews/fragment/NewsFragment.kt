package org.kichinaga.technews.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kichinaga.technews.NewsListAdapter
import org.kichinaga.technews.NewsWebView
import org.kichinaga.technews.R
import org.kichinaga.technews.apicaller.getMenthasClient
import org.kichinaga.technews.getItemNum
import org.kichinaga.technews.model.Menthas
import org.kichinaga.technews.model.News
import org.kichinaga.technews.model.Site

/**
 * Created by kichinaga on 2017/04/24.
 *
 */

class NewsFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var genre: String
    private lateinit var url: String

    companion object {
        private val GENRE_CODE: String = "GENRE"
        private val URL_CODE: String = "URL"

        fun newInstance(genre: String, url: String): NewsFragment {
            val fragment: NewsFragment = NewsFragment()
            val args: Bundle = Bundle()

            args.putString(GENRE_CODE, genre)
            args.putString(URL_CODE, url)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("NewsFragment", "onCreate")

        this.genre = arguments.getString(GENRE_CODE)
        this.url = arguments.getString(URL_CODE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("NewsFragment", "onViewCreated")

        listView = view.findViewById(R.id.news_list_view) as ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val news = listView.getItemAtPosition(position) as News

            val intent: Intent = Intent(activity, NewsWebView::class.java)
            intent.putExtra("URL", news.link)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        updateNewsList()
    }

    // TODO ニュースの更新処理を書き足す
    fun updateNewsList() {
        val item_num: Int = getItemNum(activity)
        val query: String = "select * from rss($item_num) where url = '$url'"
        val format: String = "json"

        Log.i("YQL Query", query)

        getMenthasClient().getMenthasNews(query, format).
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({
                    // onNext
                    if (it.query.count > 0) {
                        Log.i("Query", it.query.created)
                        val newsList: List<News> = it.query.results.item
                                .map { (title, description, link, _, thumbnail) -> News(0, title, description, genre, link, thumbnail.url) }
                                .toList()

                        listView.adapter = NewsListAdapter(activity, newsList)
                    }
                }, {
                    // onError
                    it.printStackTrace()
                })
    }

}