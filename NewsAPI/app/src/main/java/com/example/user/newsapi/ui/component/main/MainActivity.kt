package com.example.user.newsapi.ui.component.main

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat.invalidateOptionsMenu
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.user.newsapi.*
import com.example.user.newsapi.R.id.drawer_layout
import com.example.user.newsapi.R.id.tabs_main
import com.example.user.newsapi.callback_listener.BusinessSetUpCaller
import com.example.user.newsapi.callback_listener.MultiSelectCaller
import com.example.user.newsapi.callback_listener.NewsSetUpCaller
import com.example.user.newsapi.callback_listener.TechSetUpCaller
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.example.user.newsapi.db.Model.BookMarkModel
import com.example.user.newsapi.db.NewsApp
import com.example.user.newsapi.network.dto.Articles
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.toggleButton

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MultiSelectCaller {

    var mState = "hiden"

    var isMultiSelect = false

    var listener_news: NewsSetUpCaller? = null
    var listener_business: BusinessSetUpCaller? = null
    var listener_tech: TechSetUpCaller? = null

    var selectedIds_news: MutableList<Int> = ArrayList<Int>()
    var selectedIds_business: MutableList<Int> = ArrayList<Int>()
    var selectedIds_technology: MutableList<Int> = ArrayList<Int>()
    var localArticles_news: List<Articles> = arrayListOf()
    var localArticles_business: List<Articles> = arrayListOf()
    var localArticles_tech: List<Articles> = arrayListOf()

    var total = selectedIds_news.size + selectedIds_business.size + selectedIds_technology.size

    var news = false
    var business = false
    var tech = false

    var tabs = -1

    override fun onNewsSeletedItem(seletedId_news: MutableList<Int>, localArticles_news: List<Articles>, newsSetUpCaller: NewsSetUpCaller) {
        this.selectedIds_news = seletedId_news
        this.localArticles_news = localArticles_news
        this.listener_news = newsSetUpCaller
        listener_news?.setMultiSelect(true)
        this.isMultiSelect = true
        mState = "show"
        invalidateOptionsMenu()
        chageToolBar()
    }

    override fun onBusinessSeletedItem(seletedId_business: MutableList<Int>, localArticles_business: List<Articles>, businessSetUpCaller: BusinessSetUpCaller) {
        this.selectedIds_business = seletedId_business
        this.localArticles_business = localArticles_business
        this.listener_business = businessSetUpCaller
        listener_business?.setMultiSelect(true)
        this.isMultiSelect = true
        mState = "show"
        invalidateOptionsMenu()
        chageToolBar()
    }

    override fun onTechSeletedItem(seletedId_tech: MutableList<Int>, localArticles_tech: List<Articles>, techSetUpCaller: TechSetUpCaller) {
        this.selectedIds_technology = seletedId_tech
        this.localArticles_tech = localArticles_tech
        this.listener_tech = techSetUpCaller
        listener_tech?.setMultiSelect(true)
        this.isMultiSelect = true
        mState = "show"
        invalidateOptionsMenu()
        chageToolBar()
    }

    override fun setNewsCaller(newsSetUpCaller: NewsSetUpCaller) {
        this.listener_news = newsSetUpCaller
        if (isMultiSelect) {
            listener_news?.setMultiSelect(true)
            chageToolBar()
        }
    }

    override fun setBusinessCaller(businessSetUpCaller: BusinessSetUpCaller) {
        this.listener_business = businessSetUpCaller
        if (isMultiSelect) {
            listener_business?.setMultiSelect(true)
            chageToolBar()
        }
    }

    override fun setTechCaller(techSetUpCaller: TechSetUpCaller) {
        this.listener_tech = techSetUpCaller
        if (isMultiSelect) {
            listener_tech?.setMultiSelect(true)
            chageToolBar()
        }
    }

    override fun setActionBarState() {
        mState = "hiden"
        invalidateOptionsMenu()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)
        tabs_main.addOnTabSelectedListener((object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabs = tab!!.position
                invalidateOptionsMenu()
            }

        }))

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private var first_time: Long = 0
    private var second_time: Long = 0
    override fun onBackPressed() {
        second_time = System.currentTimeMillis()
        if (this.drawer_layout.isDrawerOpen(GravityCompat.START)) {
            this.drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            if (second_time - first_time < 2000) {
                super.onBackPressed()
                finish()
            } else Toast.makeText(this, "Press the Back button one more time to exit.", Toast.LENGTH_SHORT).show()
        }
        first_time = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (mState == "show") {
            menuInflater.inflate(R.menu.simple, menu)
            menu.getItem(1).isChecked = true
            menu.getItem(0).isChecked = true
            if ((tabs == 0) && (selectedIds_news.size == 20)) {
                menu.getItem(0).setIcon(R.drawable.baseline_check_box_white_24dp)
                menu.getItem(0).isChecked = false
            }
            if ((tabs == 1) && (selectedIds_technology.size == 20)) {
                menu.getItem(0).setIcon(R.drawable.baseline_check_box_white_24dp)
                menu.getItem(0).isChecked = false
            }
            if ((tabs == 2) && (selectedIds_business.size == 20)) {
                menu.getItem(0).setIcon(R.drawable.baseline_check_box_white_24dp)
                menu.getItem(0).isChecked = false
            }
            menu?.getItem(1)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu?.getItem(0)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menuInflater.inflate(R.menu.main, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.check_all -> {
                if (item.isChecked) {

                    item.setIcon(R.drawable.baseline_check_box_white_24dp)
                    item.isChecked = false

                    when (tabs_main.selectedTabPosition) {
                        0 -> {
                            selectedIds_news = ArrayList<Int>()
                            for (id in 0..localArticles_news.size - 1) {
                                selectedIds_news.add(id)
                            }
                            listener_news?.setSelectedIdsList(selectedIds_news)
                            chageToolBar()
                        }
                        1 -> {
                            selectedIds_technology = ArrayList<Int>()
                            for (id in 0..localArticles_tech.size - 1) {
                                selectedIds_technology.add(id)
                            }
                            listener_tech?.setSelectedIdsList(selectedIds_technology)
                            chageToolBar()
                        }
                        else -> {
                            Log.e("사이즈", "selectedIds_business: ${selectedIds_business}, localArticles_business : ${localArticles_business.size}")
                            selectedIds_business = ArrayList<Int>()
                            for (id in 0..localArticles_business.size - 1) {
                                selectedIds_business.add(id)
                            }
                            listener_business?.setSelectedIdsList(selectedIds_business)
                            chageToolBar()
                        }
                    }

                } else {
                    item.setIcon(R.drawable.baseline_check_box_outline_blank_white_24dp)
                    item.isChecked = true

                    when (tabs_main.selectedTabPosition) {
                        0 -> {
                            selectedIds_news = ArrayList<Int>()
                            listener_news?.setSelectedIdsList(selectedIds_news)
                            chageToolBar()
                        }
                        1 -> {
                            selectedIds_technology = ArrayList<Int>()
                            listener_tech?.setSelectedIdsList(selectedIds_technology)
                            chageToolBar()
                        }
                        else -> {
                            selectedIds_business = ArrayList<Int>()
                            listener_business?.setSelectedIdsList(selectedIds_business)
                            chageToolBar()
                        }
                    }
                }
                return true
            }
            R.id.book_mark -> {
                if (item.isChecked) {

                    item.setIcon(R.drawable.baseline_bookmark_white_24dp)
                    item.isChecked = false

                    addBookMark()
                    listener_news?.setUpView()
                    listener_business?.setUpView()
                    listener_tech?.setUpView()
                    selectedIds_news = ArrayList<Int>()
                    val actionBar = supportActionBar
                    actionBar!!.title = "SmartNews"
                    toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
                    invalidateOptionsMenu()
                } else {

                    /* item.setIcon(R.drawable.ic_bookmark_border_white)
                     item.isChecked = true

                     for (id in selectedIds_news) {
                         val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles_news.get(id).title!!)
                         val bookMark = NewsApp.database?.articleDao()?.findBookMarkById(articleModel?.articleId!!)

                         if (bookMark != null) {
                             Toast.makeText(applicationContext, "(${selectedIds_news.size})removed from bookmark.", Toast.LENGTH_SHORT).show()
                             NewsApp.database?.articleDao()?.deleteBookMark(bookMark)
                             listener.setUpView()
                         }
                     }

                     selectedIds_news = ArrayList<Int>()
                     listener.setUpView()
                     val actionBar = supportActionBar
                     actionBar!!.title = "SmartNews"
                     invalidateOptionsMenu()*/
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                val intent = Intent(this, BookMarkActivity::class.java)
                startActivity(intent)
                //finish()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addBookMark() {
        if (selectedIds_news.isNotEmpty()) {
            for (id in selectedIds_news) {
                val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles_news.get(id).title!!)
                val bookMark = BookMarkModel(articleModel?.articleId!!)

                AsyncTask.execute {
                    NewsApp.database?.articleDao()?.insertBookMark(bookMark)
                }
            }
        }
        if (selectedIds_business.isNotEmpty()) {
            for (id in selectedIds_business) {
                val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles_business.get(id).title!!)
                val bookMark = BookMarkModel(articleModel?.articleId!!)

                AsyncTask.execute {
                    NewsApp.database?.articleDao()?.insertBookMark(bookMark)
                }
            }
        }
        if (selectedIds_technology.isNotEmpty()) {
            for (id in selectedIds_technology) {
                val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles_tech.get(id).title!!)
                val bookMark = BookMarkModel(articleModel?.articleId!!)

                AsyncTask.execute {
                    NewsApp.database?.articleDao()?.insertBookMark(bookMark)
                }
            }
        }
        Toast.makeText(applicationContext, "(${total}) articles add to bookmark.", Toast.LENGTH_SHORT).show()
        mState = "hiden"
        chageToolBar()
        initialize()
    }

    private fun chageToolBar() {
        val actionBar = supportActionBar
        total = selectedIds_news.size + selectedIds_business.size + selectedIds_technology.size
        if (total == 0) {
            mState = "hiden"
            isMultiSelect = false
            listener_news?.setMultiSelect(false)
            listener_business?.setMultiSelect(false)
            listener_tech?.setMultiSelect(false)
            invalidateOptionsMenu()
            actionBar!!.title = "SmartNews"
            toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
            listener_business?.setUpView()
            listener_tech?.setUpView()
        } else {
            actionBar!!.title = "(${total}) Selected"
            toolbar.navigationIcon = null
        }
    }

    private fun initialize() {
        selectedIds_news = ArrayList<Int>()
        selectedIds_business = ArrayList<Int>()
        selectedIds_technology = ArrayList<Int>()
        localArticles_news = arrayListOf()
        localArticles_business = arrayListOf()
        localArticles_tech = arrayListOf()
        listener_news?.initalize()
        listener_business?.initalize()
        listener_tech?.initalize()
    }
}
