package org.kichinaga.technews.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * Created by kichinaga on 2017/04/23.
 */

open class Site(@PrimaryKey open var id: Long = 0,
                open var link: String = "",
                open var genre: String = "",
                open var name: String = "",
                open var display: Boolean = false): RealmObject()