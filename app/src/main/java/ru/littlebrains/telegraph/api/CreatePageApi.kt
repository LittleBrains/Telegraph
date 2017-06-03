package ru.littlebrains.telegraph.api

import android.app.Activity
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log
import java.net.URLEncoder

/**
 * Created by evgeniy on 03.06.2017.
 */

class CreatePageApi(activity: Activity) : BaseApi<PageModel>(activity){

    fun requestGet(access_token: String, title: String, iCallBackTApi: ICallBackTApi<PageModel>){
        val content = "[{\"tag\":\"p\",\"children\":[\"1, world!\"]}]";
        val url:String = SERVER + "/createPage" +
                "?access_token=$access_token" +
                "&title=${URLEncoder.encode(title)}" +
                "&author_name=lb" +
                "&content=${URLEncoder.encode(content,"UTF-8")}" +
                "&return_content=true"
        baseRequestGET(url, iCallBackTApi);
    }

    override fun parseThreade(resultJson: String?): PageModel {
        Log.d("createpage", resultJson)
        return PageModel()
    }

}

