package ru.littlebrains.telegraph.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import ru.littlebrains.telegraph.R
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log

/**
 * Created by evgeniy on 31.03.2017.
 */

class PagesAdapter(val dataList: List<PageModel>, val onClickListener: View.OnClickListener) : RecyclerView.Adapter<PagesAdapter.ViewHolder>() {

    class ViewHolder(v: LinearLayout) : RecyclerView.ViewHolder(v) {
        val title: TextView
        val rootView: View
        val description: TextView

        init {
            rootView = v
            title = v.findViewById(R.id.title) as TextView
            description = v.findViewById(R.id.description) as TextView
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_blog, parent, false)
        val vh = ViewHolder(v as LinearLayout)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("adapter", dataList[position].title)
        holder.title.setText(dataList[position].title)
        holder.description.setText(dataList[position].description)
        holder.rootView.tag = dataList[position].path
        holder.rootView.setOnClickListener(onClickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataList.size
    }
}
