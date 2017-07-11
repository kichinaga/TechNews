package org.kichinaga.technews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout

/**
 * Created by kichinaga on 2017/07/11.
 */

class NewsWebView: AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // intentを介して渡されたデータを取得
        val data = intent

        // layoutのセット
        val linearLayout: LinearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        setContentView(linearLayout)

        val toolbar: Toolbar = Toolbar(this)
        toolbar.title = resources.getString(R.string.app_name)
        toolbar.elevation = 12f
        toolbar.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.addView(toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        webView = WebView(this)
        webView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl(data.getStringExtra("URL"))

        linearLayout.addView(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
        // 戻るボタンが押されたら
            android.R.id.home -> {
                // 現在のActivityを終了する
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}