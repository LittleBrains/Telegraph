package ru.littlebrains.telegraph.api

import android.app.Activity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log
import java.util.ArrayList

/**
 * Created by evgeniy on 03.06.2017.
 */
class GetPageListApi(activity: Activity) : BaseApi<List<PageModel>>(activity) {

    fun requestGet(access_token: String, limit: Int, offset: Int = 0, callBack: ICallBackTApi<List<PageModel>>){
        val url: String = SERVER + "getPageList?" +
                "access_token="+access_token+"&" +
                "offset="+offset+"&" +
                "limit="+limit;
        baseRequestGET(url, callBack);
    }

    override fun parseThreade(resultJson: String?): List<PageModel> {
        val listPages = ArrayList<PageModel>()
        try{
            val jsonObject = JSONObject(resultJson)
            val ok = jsonObject.getBoolean("ok")
            if(!ok) return listPages
            val jsonArray = jsonObject.getJSONObject("result").getJSONArray("pages")
            for(i in 0..jsonArray.length()-1){
                val title = jsonArray.getJSONObject(i).getString("title")
                val url = jsonArray.getJSONObject(i).getString("url")
                val path = jsonArray.getJSONObject(i).getString("path")
                val description = jsonArray.getJSONObject(i).getString("description")
                var author_name = ""
                if(jsonArray.getJSONObject(i).has("author_name")){
                    author_name = jsonArray.getJSONObject(i).getString("author_name")
                }
                var image_url = ""
                if(jsonArray.getJSONObject(i).has("image_url")) {
                    image_url = jsonArray.getJSONObject(i).getString("image_url")
                }
                val views = jsonArray.getJSONObject(i).getInt("views")
                val can_edit = jsonArray.getJSONObject(i).getBoolean("can_edit")

                listPages.add(PageModel(ok, "", path, url, title, description, author_name, image_url, views, can_edit))
            }
            return listPages
        }catch (e: JSONException){
            Log.e(e)
            return listPages
        }
    }
}