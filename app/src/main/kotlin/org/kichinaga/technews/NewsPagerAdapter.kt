package org.kichinaga.technews

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.kichinaga.technews.fragment.NewsFragment
import org.kichinaga.technews.model.Site

/**
 * Created by kichinaga on 2017/04/24.
 */

class NewsPagerAdapter(fm:FragmentManager,val sites: List<Site>): FragmentStatePagerAdapter(fm){

    override fun getPageTitle(position: Int): CharSequence = sites[position].genre

    override fun getItem(position: Int): Fragment = NewsFragment.newInstance(sites[position].genre, sites[position].link)

    override fun getCount(): Int = sites.size
}