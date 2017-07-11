package org.kichinaga.technews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import org.kichinaga.technews.model.News

/**
 * Created by kichinaga on 2017/07/06.
 */

class NewsListAdapter(val context: Context, val list: List<News>): BaseAdapter() {

    val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contentView: View
        if (convertView != null) {
            contentView = convertView
        } else {
            contentView = layoutInflater.inflate(R.layout.list_item, parent, false)
        }
        val viewHolder: ViewHolder = ViewHolder()

        /*
        contentView.bindView<TextView>(R.id.title).value.text = list[position].title
        if (list[position].thumbnail != "") {
            Picasso.with(context)
                    .load(list[position].thumbnail)
                    .into(contentView.bindView<ImageView>(R.id.thumbnail).value)
        }
        */

        // viewholderの設定
        viewHolder.textView = contentView.findViewById(R.id.title) as TextView
        viewHolder.imageView = contentView.findViewById(R.id.thumbnail) as ImageView
        viewHolder.animationView = contentView.findViewById(R.id.favorite_button) as LottieAnimationView
        contentView.tag = viewHolder

        // viewにデータをセット
        viewHolder.textView.text = list[position].title
        if (list[position].thumbnail != "") {
            Picasso.with(context)
                    .load(list[position].thumbnail)
                    .into(viewHolder.imageView)
        }

        viewHolder.animationView.setOnClickListener {
            val realm = Realm.getInstance(getConfiguration(NEWS_REALM))
            // onClick
            if (viewHolder.clickedFav) {
                // 有効状態でクリック
                realm.executeTransactionAsync({
                    // execute
                    // 同じ記事データが存在したとき、そのデータを削除する
                    val result: RealmResults<News> = it.where(News::class.java).equalTo("link", list[position].link).findAll()
                    if (result.size > 0) {
                        Log.d("RealmDataDelete", result.size.toString())
                        // マッチしたObjectを1つ削除
                        result.deleteFromRealm(0)
                    }
                },{
                    // onSuccess
                    viewHolder.animationView.progress = 0f
                    viewHolder.clickedFav = false
                },{
                    // onError
                    Toast.makeText(context,"true: ${it.message}",Toast.LENGTH_LONG).show()
                })
            } else {
                // todo list[position]のデータをお気に入りへ入れる
                realm.executeTransactionAsync({
                    // execute
                    val news: News = list[position]
                    // idの更新
                    // 重複しないように[最後の要素のid + 1]した値をidとして使用
                    val result: RealmResults<News> = it.where(News::class.java).findAll().sort("id", Sort.ASCENDING)
                    if (result.size != 0) {
                        news.id = result.last().id + 1
                    } else {
                        news.id = 0
                    }

                    it.insertOrUpdate(news)
                },{
                    // onSuccess
                    viewHolder.animationView.playAnimation()
                    viewHolder.clickedFav = true
                },{
                    // onError
                    Toast.makeText(context,"false: ${it.message}",Toast.LENGTH_LONG).show()
                })

            }
            realm.close()
        }

        return contentView
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }


    class ViewHolder {
        var clickedFav: Boolean = false
        lateinit var textView: TextView
        lateinit var imageView: ImageView
        lateinit var animationView: LottieAnimationView
    }

}