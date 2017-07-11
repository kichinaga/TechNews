package org.kichinaga.technews

import android.app.Application
import android.content.Context
import android.support.annotation.IdRes
import android.view.View

/**
 * Created by kichinaga on 2017/04/23.
 */

fun <T: View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
    findViewById(id) as T
}


// Settings
private val PREF_SETTING: String = "settings"
private val ITEM_NUM: String = "item_num"

fun setItemNum(context:Context, num: Int) {
    context.getSharedPreferences(PREF_SETTING, Application.MODE_PRIVATE).edit().putInt(ITEM_NUM, num).apply()
}

fun getItemNum(context: Context):Int {
    return context.getSharedPreferences(PREF_SETTING, Application.MODE_PRIVATE).getInt(ITEM_NUM, 20)
}