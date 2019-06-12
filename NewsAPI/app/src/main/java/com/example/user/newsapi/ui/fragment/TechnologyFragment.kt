package com.example.user.newsapi.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import com.example.user.newsapi.callback_listener.MethodCaller
import com.example.user.newsapi.callback_listener.MultiSelectCaller
import com.example.user.newsapi.R
import com.example.user.newsapi.callback_listener.BusinessSetUpCaller
import com.example.user.newsapi.callback_listener.TechSetUpCaller
import com.example.user.newsapi.db.Model.ArticleModel
import com.example.user.newsapi.db.Model.BookMarkModel
import com.example.user.newsapi.db.NewsApp
import com.example.user.newsapi.network.api.RetroBaseApiService
import com.example.user.newsapi.network.dto.Articles
import com.example.user.newsapi.network.dto.Source
import com.example.user.newsapi.ui.component.detail.DetailedActivity
import com.example.user.newsapi.ui.component.main.MainActivity
import com.example.user.newsapi.ui.component.main.MainViewAdapter
import com.example.user.newsapi.ui.listner.RecyclerItemListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_technology.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TechnologyFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var apiService: RetroBaseApiService
    lateinit var country: String
    lateinit var category: String
    val apikey = "810322576cc748a2a15e633256642ba2"

    var localArticles: List<Articles> = arrayListOf()
    var selectedIds: MutableList<Int> = ArrayList<Int>()
    lateinit var localDBArticles: MutableList<ArticleModel>
    lateinit var selectDBArticles: List<ArticleModel>
    lateinit var selectArticles: MutableList<Articles>

    var isMultiSelect = false

    var activityCallback: MultiSelectCaller? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCallback = context as MultiSelectCaller
        } catch (e: ClassCastException) {
            throw ClassCastException(context?.toString()
                    + " must implement ToolbarListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_technology, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        apiService = retrofit.create(RetroBaseApiService::class.java)
        country = "Us"
        category = "Technology"
        getnews()

        spinner_country2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                country = parent?.getItemAtPosition(position) as String
                getnews()
            }
        }
    }

    val techSetUpCaller = object : TechSetUpCaller {
        override fun setSelectedIdsList(seletedId_tech: MutableList<Int>) {
            selectedIds = seletedId_tech
            setUpRecyclerViewWithCheckBox(localArticles)
        }

        override fun initalize() {
            selectedIds = ArrayList<Int>()
        }

        override fun setMultiSelect(value: Boolean) {
            isMultiSelect = value
        }

        override fun setUpView() {
            selectedIds = ArrayList<Int>()
            setUpRecyclerView(localArticles)
        }
    }

    private val recyclerItemListener = object : RecyclerItemListener {
        override fun onLongItemSelected(position: Int): Boolean {
            isMultiSelect = true
            methodCaller.updateFirstCheckBox()
            addIDIntoSelectedIds(position)
            activityCallback?.onTechSeletedItem(selectedIds, localArticles, techSetUpCaller)
            setUpRecyclerViewWithCheckBox(localArticles)
            setHasOptionsMenu(true)
            return true
        }

        override fun onItemSelected(position: Int) {
            activityCallback?.setTechCaller(techSetUpCaller)
            if (isMultiSelect) {
                addIDIntoSelectedIds(position)
                activityCallback?.onTechSeletedItem(selectedIds, localArticles, techSetUpCaller)
            } else {
                val intent = Intent(activity, DetailedActivity::class.java)
                intent.putExtra("article", localArticles[position])
                startActivity(intent)
            }
        }
    }

    fun addIDIntoSelectedIds(index: Int) {
        val id = index
        if (selectedIds.contains(id)) {
            selectedIds.remove(id)
        } else {
            selectedIds.add(id)
        }
        setUpRecyclerViewWithCheckBox(localArticles)
        if (selectedIds.size < 1) {
            isMultiSelect = false
            setUpRecyclerView(localArticles)
            activityCallback?.setActionBarState()
        }
    }

    val methodCaller = object : MethodCaller {
        override fun shareArticle(description: String?) {
            val shareIntent = ShareCompat.IntentBuilder.from(activity as MainActivity)
                    .setType("text/plain")
                    .setText(description)
                    .intent
            if (shareIntent.resolveActivity(context?.packageManager) != null) {
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
            activityCallback?.onTechSeletedItem(selectedIds, localArticles, techSetUpCaller)

        }

        override fun updateUnSelectedCount(Index: Int) {
            addIDIntoSelectedIds(Index)
            activityCallback?.onTechSeletedItem(selectedIds, localArticles, techSetUpCaller)
            if (selectedIds.size < 1) {
                isMultiSelect = false
                setUpRecyclerView(localArticles)
                activityCallback?.setActionBarState()
            }

        }
    }

    private fun setUpRecyclerView(articles: List<Articles>) {
        recycler_view_news_list2.layoutManager = LinearLayoutManager(activity)
        recycler_view_news_list2.setHasFixedSize(true)
        recycler_view_news_list2.adapter = MainViewAdapter(articles, recyclerItemListener, false, methodCaller)
    }

    private fun setUpRecyclerViewWithCheckBox(articles: List<Articles>) {
        recycler_view_news_list2.layoutManager = LinearLayoutManager(activity)
        recycler_view_news_list2.setHasFixedSize(true)
        recycler_view_news_list2.adapter = MainViewAdapter(articles, recyclerItemListener, true, methodCaller)
    }

    private fun getnews() {

        val localCategory: String? = if ("Headline" == category) {
            null
        } else {
            category
        }

        if (isNetworkAvailable()) {

            apiService.getResponse(country, localCategory, apikey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    { result ->
                        localArticles = result.articles
                        activityCallback?.onTechSeletedItem(selectedIds, localArticles, techSetUpCaller)
                        setUpRecyclerView(result.articles)
                        pb_loading2.visibility = View.GONE
                        insertDb(result.articles)
                    }, { e ->
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(activity, "Offline service", Toast.LENGTH_SHORT).show()
            localArticles = selectDB()
            setUpRecyclerView(localArticles)
            pb_loading2.visibility = View.GONE
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun insertDb(articles: List<Articles>) {
        localDBArticles = arrayListOf()
        for (article in articles) {
            var articleModel = ArticleModel()
            articleModel.author = article.author
            articleModel.description = article.description
            articleModel.title = article.title
            Log.e("title", article.title)
            articleModel.url = article.url
            articleModel.urlToImage = article.urlToImage
            articleModel.publishedAt = article.publishedAt
            articleModel.id = article.source.id
            articleModel.name = article.source.name
            localDBArticles.add(articleModel)
        }
        AsyncTask.execute {
            NewsApp.database?.articleDao()?.insertArticle(localDBArticles)
        }
    }

    private fun selectDB(): List<Articles> {
        selectArticles = arrayListOf()
        selectDBArticles = NewsApp.database?.articleDao()?.getAllArticles()!!
        for (articleModel in selectDBArticles) {
            var source = Source(id = articleModel.id, name = articleModel.name!!)
            var article = Articles(author = articleModel.author, title = articleModel.title, description = articleModel.description, url = articleModel.url, urlToImage = articleModel.urlToImage, publishedAt = articleModel.publishedAt, source = source)

            selectArticles.add(article)
        }
        return selectArticles
    }

}