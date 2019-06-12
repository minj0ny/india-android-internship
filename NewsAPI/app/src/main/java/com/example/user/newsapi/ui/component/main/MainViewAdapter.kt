package com.example.user.newsapi.ui.component.main

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.newsapi.callback_listener.MethodCaller
import com.example.user.newsapi.R
import com.example.user.newsapi.network.dto.Articles
import com.example.user.newsapi.ui.listner.RecyclerItemListener
import kotlinx.android.synthetic.main.news_item.view.*

class MainViewAdapter(private val news: List<Articles>, private val recyclerItemListener: RecyclerItemListener, private val check: Boolean, private val updateSelectedListener: MethodCaller) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false), updateSelectedListener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder?.itemView?.setOnClickListener {
            recyclerItemListener.onItemSelected(position)
        }
        holder?.itemView?.setOnLongClickListener {
            recyclerItemListener.onLongItemSelected(position)
        }
        val id = position

        if (updateSelectedListener.updateFirstCheckBox().contains(id)) {
            holder?.itemView?.setBackgroundColor(Color.parseColor("#10000000"))
            holder?.itemView?.checkBox?.isChecked = true
        }
        holder?.bind(position, news[position], check)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}