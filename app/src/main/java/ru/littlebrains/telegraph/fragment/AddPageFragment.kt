package ru.littlebrains.telegraph.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import ru.littlebrains.telegraph.R
import ru.littlebrains.telegraph.Utils
import ru.littlebrains.telegraph.api.CreatePageApi
import ru.littlebrains.telegraph.api.ICallBackTApi
import ru.littlebrains.telegraph.api.RequestException
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log


/**
 * A simple [Fragment] subclass.
 */
class AddPageFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if(isNoRotated) return rootView
        rootView = inflater!!.inflate(R.layout.fragment_add_page, container, false)

        val btnPublish = rootView?.findViewById(R.id.btn_publish) as Button
        btnPublish.setOnClickListener(View.OnClickListener { v: View ->
            setProgressLayout(true);
            val token = Utils.getSharedPreferences(context).getString("token", "")
            val title = (rootView?.findViewById(R.id.title) as EditText).text.toString()
            val content = (rootView?.findViewById(R.id.content) as EditText).text.toString()
            CreatePageApi(activity).requestGet(token, title, content, object : ICallBackTApi<PageModel> {
                override fun onComplete(result: PageModel) {
                    setProgressLayout(false)
                    if(result.ok){
                        baseActivity.backFragmet()
                        Toast.makeText(activity, "Story saved", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, result.ok.toString() + " " + result.error, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onException(e: RequestException) {
                    setProgressLayout(false)
                    Log.d("CreatePageApi", e)
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            })
        })

        return rootView
    }

}// Required empty public constructor
