package org.kichinaga.technews

import android.app.Application
import io.realm.Realm
import android.R.id.edit
import org.kichinaga.technews.model.Site
import android.R.array
import io.realm.RealmConfiguration


/**
 * Created by kichinaga on 2017/04/24.
 */
class TechNewsApplication : Application() {

    companion object {
        private val PREF_SEED: String = "seeds"
        private val SEED_FRAG: String = "flags"
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        setItemNum(this, 20)
        insertSeedsData()
    }

    //初期データを格納する処理
    private fun insertSeedsData() {
        val realm = Realm.getInstance(getConfiguration(SITE_REALM))

        realm.executeTransaction {
            it.insertOrUpdate(Site(0,"http://menthas.com/top/rss", "top", "menthas", true))
            it.insertOrUpdate(Site(1,"http://menthas.com/gadget/rss", "gadget", "menthas", true))
            it.insertOrUpdate(Site(2,"http://menthas.com/android/rss", "android", "menthas", true))
            it.insertOrUpdate(Site(3,"http://menthas.com/ios/rss", "ios", "menthas", true))
            it.insertOrUpdate(Site(4,"http://menthas.com/ruby/rss", "ruby", "menthas", true))
            it.insertOrUpdate(Site(5,"http://menthas.com/java/rss", "java", "menthas", true))
        }

        seedInsertComplete()
        realm.close()
    }

    @Synchronized private fun seedInsertComplete() {
        getSharedPreferences(PREF_SEED,MODE_PRIVATE).edit().putBoolean(SEED_FRAG, true).apply()
    }

    @Synchronized private fun isSeedFrag(): Boolean {
        return getSharedPreferences(PREF_SEED,MODE_PRIVATE).getBoolean(SEED_FRAG, false)
    }
}