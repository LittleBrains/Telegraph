package ru.littlebrains.telegraph.fragment


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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

    var size = 0;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

       // if(isNoRotated) return rootView
        rootView = inflater!!.inflate(R.layout.fragment_main, container, false)

        var recyclerView: RecyclerView = rootView?.findViewById(R.id.list_pages) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(mActivity)
        val layoutManager = mLayoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setLayoutManager(mLayoutManager)

        setProgressLayout(true)
        val token = Utils.getSharedPreferences(context).getString("token", "");
        GetPageListApi(activity).requestGet(token, 100, 0, object : ICallBackTApi<List<PageModel>> {
            override fun onComplete(result: List<PageModel>) {
                setProgressLayout(false)
                val adapter = PagesAdapter(result, View.OnClickListener { v ->
                    Log.d(v.getTag())
                    baseActivity.newFragment(PageFragment.newInstance(v.getTag().toString()), R.layout.fragment_page, true)
                })
                recyclerView.adapter = adapter
                if(size != result.size){
                    size = result.size
                    recyclerView.scrollToPosition(0)
                }
            }

            override fun onException(e: RequestException) {
                setProgressLayout(false)
            }
        })

        val fabAddPage = rootView?.findViewById(R.id.fab_add_page) as FloatingActionButton
        fabAddPage.setOnClickListener(View.OnClickListener { v:View ->
            baseActivity.newFragment(AddPageFragment(), R.layout.fragment_add_page, true)
        })

        return rootView
    }

    override fun onResume() {
        super.onResume()

    }

}// Required empty public constructor
