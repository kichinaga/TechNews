package org.kichinaga.technews

import io.realm.RealmConfiguration

/**
 * Created by kichinaga on 2017/07/06.
 */

val SITE_REALM: String = "site"
val NEWS_REALM: String = "news"

fun getConfiguration(name: String, version: Long = 0): RealmConfiguration {
    return RealmConfiguration.Builder()
            .name(name)
            .schemaVersion(version)
            .deleteRealmIfMigrationNeeded()
            .build()
}