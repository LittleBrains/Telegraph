package ru.littlebrains.telegraph.api

import android.app.Activity
import org.json.JSONException
import org.json.JSONObject
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log
import java.net.URLEncoder

/**
 * Created by evgeniy on 03.06.2017.
 */

class CreatePageApi(activity: Activity) : BaseApi<PageModel>(activity){

    fun requestGet(access_token: String, title: String, content: String, iCallBackTApi: ICallBackTApi<PageModel>){
        val content = "[{\"tag\":\"p\",\"children\":[\"$content\"]}]";
        val url:String = SERVER + "/createPage" +
                "?access_token=$access_token" +
                "&title=${URLEncoder.encode(title)}" +
                "&author_name=lb" +
                "&content=${URLEncoder.encode(content,"UTF-8")}" +
                "&return_content=true"
        baseRequestGET(url, iCallBackTApi);
    }

    override fun parseThreade(resultJson: String?): PageModel {
        try{
            val jsonObject = JSONObject(resultJson)
            val ok = jsonObject.getBoolean("ok")
            if(!ok) return PageModel(ok, jsonObject.getString("error"));

            return PageModel(ok);
        }catch (e: JSONException){
            Log.e(e);
        }
        return PageModel(false)
    }

}

