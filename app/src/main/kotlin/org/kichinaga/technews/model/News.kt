package org.kichinaga.technews.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Created by kichinaga on 2017/04/24.
 * とってきたニュースをお気に入りに保存するときのモデル
 *
 * 構成
 * id               --- 主キー
 * genre            --- ニュースのジャンル
 * title            --- 記事のタイトル
 * link             --- 記事元のリンク
 * contentSnippet   --- 記事の内容の最初部分を切り取ったもの
 */

open class News(@PrimaryKey open var id: Int = 0,
                open var title: String = "",
                open var description: String = "",
                open var genre: String = "",
                open var link: String = "",
                open var thumbnail: String = ""): RealmObject()
