package com.example.user.newsapi

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.example.user.newsapi.callback_listener.MethodCaller
import com.example.user.newsapi.db.Model.BookMarkModel
import com.example.user.newsapi.db.NewsApp
import com.example.user.newsapi.network.dto.Articles
import com.example.user.newsapi.network.dto.Source
import com.example.user.newsapi.ui.component.detail.DetailedActivity
import com.example.user.newsapi.ui.component.main.MainActivity
import com.example.user.newsapi.ui.component.main.MainViewAdapter
import com.example.user.newsapi.ui.listner.RecyclerItemListener
import kotlinx.android.synthetic.main.activity_book_mark.*

class BookMarkActivity : AppCompatActivity() {

    lateinit var localArticles: List<Articles>
    lateinit var selectDBBookMarks: List<BookMarkModel>
    lateinit var selectBookMarks: MutableList<Articles>

    var mState = "hiden" // setting state

    var isMultiSelect = false

    var selectedIds: MutableList<Int> = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_mark)
        getnews()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    private val recyclerItemListener = object : RecyclerItemListener {
        override fun onLongItemSelected(position: Int): Boolean {
            isMultiSelect = true
            methodCaller.updateFirstCheckBox()
            addIDIntoSelectedIds(position)
            setUpRecyclerViewWithCheckBox(localArticles)
            mState = "show"
            invalidateOptionsMenu()
            chageToolBar()
            Toast.makeText(applicationContext, "" + position, Toast.LENGTH_LONG).show()
            return true
        }

        override fun onItemSelected(position: Int) {
            if (isMultiSelect) {
                addIDIntoSelectedIds(position)
            } else {
                val intent = Intent(applicationContext, DetailedActivity::class.java)
                intent.putExtra("article", localArticles[position])
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmark, menu)
        menu?.getItem(1)?.isChecked = true
        menu?.getItem(0)?.isChecked = true
        menu?.findItem(R.id.home)?.setCheckable(true)

        if (mState == "show") {
            menu?.getItem(1)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu?.getItem(0)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            mState = "hiden"
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           R.id.home -> {
               /*val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()*/
               onBackPressed()
                return true
            }
            R.id.action_settings -> return true
            R.id.check_all -> {
                if (item.isChecked()) {
                    selectedIds = ArrayList<Int>()
                    for (i in 0..localArticles.size - 1) {
                        selectedIds.add(i)
                    }
                    chageToolBar()
                    setUpRecyclerViewWithCheckBox(localArticles)
                    item.setIcon(R.drawable.baseline_check_box_white_24dp)
                    item.isChecked = false
                } else {
                    selectedIds = arrayListOf<Int>()
                    chageToolBar()
                    setUpRecyclerViewWithCheckBox(localArticles)
                    item.setIcon(R.drawable.baseline_check_box_white_24dp)
                    item.isChecked = false
                    item.setIcon(R.drawable.baseline_check_box_outline_blank_white_24dp)
                    item.isChecked = true
                }
                return true
            }
            R.id.book_mark -> {
                if (item.isChecked) {

                    item.setIcon(R.drawable.ic_bookmark_border_white)
                    item.isChecked = true

                    for (id in selectedIds) {
                        val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles.get(id).title!!)
                        val bookMark = NewsApp.database?.articleDao()?.findBookMarkById(articleModel?.articleId!!)

                        if (bookMark != null) {
                            Toast.makeText(applicationContext, "(${selectedIds.size})removed from bookmark.", Toast.LENGTH_SHORT).show()
                            NewsApp.database?.articleDao()?.deleteBookMark(bookMark)
                        }
                    }
                    selectedIds = ArrayList<Int>()
                    localArticles = selectDB()
                    setUpRecyclerView(localArticles)
                    val actionBar = supportActionBar
                    actionBar!!.title = "SmartNews"
                    invalidateOptionsMenu()

                } else {
                    item.setIcon(R.drawable.baseline_bookmark_white_24dp)
                    item.isChecked = false

                    for (id in selectedIds) {
                        Toast.makeText(applicationContext, "(${selectedIds.size}) articles add to bookmark.", Toast.LENGTH_SHORT).show()
                        val articleModel = NewsApp.database?.articleDao()?.findArticleById(localArticles.get(id).title!!)
                        val bookMark = BookMarkModel(articleModel?.articleId!!)

                        AsyncTask.execute {
                            NewsApp.database?.articleDao()?.insertBookMark(bookMark)
                        }
                    }
                    selectedIds = ArrayList<Int>()
                    setUpRecyclerView(localArticles)
                    val actionBar = supportActionBar
                    actionBar!!.title = "SmartNews"
                    invalidateOptionsMenu()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun addIDIntoSelectedIds(index: Int) {
        val id = index
        if (selectedIds.contains(id)) {
            selectedIds.remove(id)
            chageToolBar()
        } else {
            selectedIds.add(id)
            chageToolBar()
        }
        setUpRecyclerViewWithCheckBox(localArticles)
        if (selectedIds.size < 1) {
            isMultiSelect = false
            setUpRecyclerView(localArticles)
            val actionBar = supportActionBar
            actionBar!!.title = "SmartNews"
            invalidateOptionsMenu()
        }
    }

    val methodCaller = object : MethodCaller {
        override fun shareArticle(description: String?) {
            val shareIntent = ShareCompat.IntentBuilder.from(this@BookMarkActivity)
                    .setType("text/plain")
                    .setText(description)
                    .intent
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
        }

        override fun updateFirstCheckBox(): MutableList<Int> {
            return selectedIds
        }

        override fun updateView() {
            getnews()
        }

        override fun updateSelectedCount(Index: Int) {
            addIDIntoSelectedIds(Index)
            chageToolBar()

        }

        override fun updateUnSelectedCount(Index: Int) {
            addIDIntoSelectedIds(Index)
            chageToolBar()
            if (selectedIds.size < 1) {
                isMultiSelect = false
                setUpRecyclerView(localArticles)
                val actionBar = supportActionBar
                actionBar!!.title = "SmartNews"
                invalidateOptionsMenu()
            }

        }
    }

    private fun setUpRecyclerView(articles: List<Articles>) {
        recycler_view_bookmark_list.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view_bookmark_list.hasFixedSize()
        recycler_view_bookmark_list.adapter = MainViewAdapter(articles, recyclerItemListener, false, methodCaller)
    }

    private fun setUpRecyclerViewWithCheckBox(articles: List<Articles>) {
        recycler_view_bookmark_list.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view_bookmark_list.hasFixedSize()
        recycler_view_bookmark_list.adapter = MainViewAdapter(articles, recyclerItemListener, true, methodCaller)
    }

    private fun getnews() {
        pb_loading_bookmark.visibility = View.GONE
        localArticles = selectDB()
        setUpRecyclerView(localArticles)
    }

    private fun selectDB(): List<Articles> {
        selectBookMarks = arrayListOf()
        selectDBBookMarks = NewsApp.database?.articleDao()?.getAllBookMarks()!!
        for (bookMarkModel in selectDBBookMarks) {
            var articleModel = NewsApp.database?.articleDao()?.findArticleByarticleID(bookMarkModel.articleId!!)!!
            var source = Source(id = articleModel.id, name = articleModel.name!!)
            var article = Articles(author = articleModel.author, title = articleModel.title, description = articleModel.description, url = articleModel.url, urlToImage = articleModel.urlToImage, publishedAt = articleModel.publishedAt, source = source)

            selectBookMarks.add(article)
        }
        return selectBookMarks
    }

    private fun chageToolBar() {
        val actionBar = supportActionBar
        actionBar!!.title = "(${selectedIds.size}) Selected"
    }
}

