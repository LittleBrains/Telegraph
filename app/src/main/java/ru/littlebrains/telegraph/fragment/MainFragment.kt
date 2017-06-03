package ru.littlebrains.telegraph.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.littlebrains.telegraph.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ru.littlebrains.telegraph.Utils
import ru.littlebrains.telegraph.adapter.PagesAdapter
import ru.littlebrains.telegraph.api.GetPageListApi
import ru.littlebrains.telegraph.api.ICallBackTApi
import ru.littlebrains.telegraph.api.RequestException
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        if(rootView != null) return rootView
        rootView = inflater!!.inflate(R.layout.fragment_main, container, false)

        var recyclerView: RecyclerView = rootView?.findViewById(R.id.list_pages) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(mActivity)
        val layoutManager = mLayoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setLayoutManager(mLayoutManager)

        val token = Utils.getSharedPreferences(context).getString("token", "");
        GetPageListApi(activity).requestGet(token, 10, 0, object : ICallBackTApi<List<PageModel>> {
            override fun onComplete(result: List<PageModel>) {
                Log.d("size", result.size)
                val adapter = PagesAdapter(result, View.OnClickListener { v ->
                    Log.d(v.getTag())
                })
                recyclerView.adapter = adapter

            }

            override fun onException(e: RequestException) {
            }
        })

        return rootView
    }

}// Required empty public constructor
