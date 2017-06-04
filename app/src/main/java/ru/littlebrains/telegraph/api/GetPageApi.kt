package ru.littlebrains.telegraph.api

import android.app.Activity
import org.json.JSONException
import org.json.JSONObject
import ru.littlebrains.telegraph.model.PageModel
import ru.littlebrains.telegraph.model.ViewPageModel
import trikita.log.Log
import java.util.ArrayList

/**
 * Created by evgeniy on 04.06.2017.
 */
class GetPageApi(activity: Activity) : BaseApi<ViewPageModel>(activity) {

    fun requestGet(path: String, callBack: ICallBackTApi<ViewPageModel>){
        val url: String = SERVER + "getPage/$path?" +
                "return_content=true";
        baseRequestGET(url, callBack);
    }

    override fun parseThreade(resultJson: String): ViewPageModel {
        try {
            val jsonObject = JSONObject(resultJson)
            val ok = jsonObject.getBoolean("ok")
            if (!ok) return ViewPageModel(ok, jsonObject.getString("error"));

            val title = jsonObject.getJSONObject("result").getString("title")
            val url = jsonObject.getJSONObject("result").getString("url")
            val path = jsonObject.getJSONObject("result").getString("path")
            var author_name = ""
            if (jsonObject.getJSONObject("result").has("author_name")) {
                author_name = jsonObject.getJSONObject("result").getString("author_name")
            }
            var content = "";
            if(jsonObject.getJSONObject("result").has("content")) {
                content = jsonObject.getJSONObject("result").getString("content")
            }

            return ViewPageModel(ok, "", path, url, title, author_name, content)
        }catch (e: JSONException){
            Log.e(e)
            return return ViewPageModel(false, e.message as String);
        }
    }
}