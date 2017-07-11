package org.kichinaga.technews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import io.realm.Realm
import io.realm.RealmResults
import org.kichinaga.technews.model.Site
import org.kichinaga.technews.R.id.toolbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.design.widget.NavigationView
import android.view.Menu
import android.view.SubMenu
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private val realm: Realm = Realm.getInstance(getConfiguration(SITE_REALM))

    companion object {
        private val NAV_GROUP_ID: Int = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val tab_layout: TabLayout = findViewById(R.id.tab_layout) as TabLayout
        val view_pager: ViewPager = findViewById(R.id.view_pager) as ViewPager

        view_pager.adapter = NewsPagerAdapter(supportFragmentManager, updateSiteList())
        tab_layout.setupWithViewPager(view_pager)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val menu: Menu = navigationView.menu
        val subMenu: SubMenu = menu.addSubMenu(NAV_GROUP_ID, Menu.NONE, Menu.NONE, "ニュースサイト一覧")
        subMenu.add(NAV_GROUP_ID, NAV_GROUP_ID + 1, Menu.NONE, "Menthas")
        subMenu.add(NAV_GROUP_ID, NAV_GROUP_ID + 2, Menu.NONE, "Gigazine")
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                101 -> Toast.makeText(this, "menthas", Toast.LENGTH_LONG).show()
            }
            super.onOptionsItemSelected(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun updateSiteList(): List<Site> {
        //登録されているサイトを取得
        val result: RealmResults<Site> = realm.where(Site::class.java).findAll()

        val list: List<Site> = result
                .filter { it.display }
                .toList()

        return list
    }
}
