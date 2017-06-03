package ru.littlebrains.telegraph.api

import android.app.Activity
import org.json.JSONException
import org.json.JSONObject

import ru.littlebrains.telegraph.model.AccountModel
import trikita.log.Log

/**
 * Created by evgeniy on 03.06.2017.
 */

class CreateAccountApi(activity: Activity) : BaseApi<AccountModel>(activity) {


    fun requestGet(short_name: String, author_name: String, author_url: String, callBackApi: ICallBackTApi<AccountModel>) {
        //https://api.telegra.ph/createAccount?short_name=Sandbox&author_name=Anonymous
        val url = BaseApi.SERVER + "createAccount?" +
                "short_name=" + short_name + "&" +
                "author_name=" + author_name + "&" +
                "author_url=" + author_url
        baseRequestGET(url, callBackApi)
    }

    override fun parseThreade(resultJson: String): AccountModel {
        Log.d("api request", resultJson)
        try{
            val jsonObject = JSONObject(resultJson);
            val ok = jsonObject.getBoolean("ok");
            if(!ok) return AccountModel(false);
            val short_name = jsonObject.getJSONObject("result").getString("short_name");
            val author_name = jsonObject.getJSONObject("result").getString("author_name");
            val author_url = jsonObject.getJSONObject("result").getString("author_url");
            val access_token = jsonObject.getJSONObject("result").getString("access_token");
            val auth_url = jsonObject.getJSONObject("result").getString("auth_url");
            return AccountModel(true, short_name, author_name, author_url, access_token, auth_url);
        }catch (e: JSONException){
            Log.e(e);
            return AccountModel(false);
        }
    }
}
