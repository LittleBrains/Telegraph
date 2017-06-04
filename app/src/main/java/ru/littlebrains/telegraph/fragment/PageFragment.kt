package ru.littlebrains.telegraph.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException

import ru.littlebrains.telegraph.R
import ru.littlebrains.telegraph.api.GetPageApi
import ru.littlebrains.telegraph.api.ICallBackTApi
import ru.littlebrains.telegraph.api.RequestException
import ru.littlebrains.telegraph.model.PageModel
import ru.littlebrains.telegraph.model.ViewPageModel
import trikita.log.Log
import android.content.Intent




/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFragment : BaseFragment() {

    private var path = ""
    private var url = ""

    companion object {
        private val PATH = "path"

        fun newInstance(path: String): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putString(PATH, path)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            path = arguments.getString(PATH)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if(isNoRotated) return rootView
        rootView = inflater!!.inflate(R.layout.fragment_page, container, false)

        setProgressLayout(true)
        GetPageApi(activity).requestGet(path, object: ICallBackTApi<ViewPageModel>{
            override fun onComplete(result: ViewPageModel) {
                setProgressLayout(false)
                if(!result.ok) Toast.makeText(context, result.error, Toast.LENGTH_LONG).show()

                (rootView?.findViewById(R.id.title) as TextView).text = result.title
                (rootView?.findViewById(R.id.content) as TextView).text = parseContent(result.content)
                url = result.url
            }

            override fun onException(e: RequestException) {
                setProgressLayout(false)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

        })
        return rootView
    }

    private fun  parseContent(content: String): String {
        var text = "";
        try{
            val jsonArray = JSONArray(content)
            for(i in 0..jsonArray.length()-1){
                text += jsonArray.getJSONObject(i).getJSONArray("children").getString(0)+"\n"
            }
        }catch (e: JSONException){
            Log.e(e);
        }
        return text
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, url)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
                return true
            }
        }

        return false
    }


}// Required empty public constructor
