package ru.littlebrains.telegraph.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.NameValuePair;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import trikita.log.Log;

abstract public class BaseApi<T> {


    public static final String SERVER = "https://api.telegra.ph/";
    private Context mContext;
    protected Activity mActivity;

    public BaseApi(Activity activity){
        mActivity = activity;
    }
    public BaseApi(Context context){
        mContext = context;
    }

    protected void baseRequestGET(final String url, final ICallBackTApi<T> callBackApi){
        Log.d("url reques", url);
        Builders.Any.B ion = null;
        if(mActivity != null){
            ion = Ion.with(mActivity).load(url);
        }else{
            ion = Ion.with(mContext).load(url);
        }
        ion.noCache()
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(callBackApi == null) return;
                        if(isException(url, e, callBackApi)) return;
                        if (result != null) {
                            switch (result.getHeaders().code()){
                                case 200:
                                    Log.d(200);
                                    if(mActivity != null) {
                                        new Thread(new ParseRunnable(url, callBackApi, result.getResult(), "")).start();
                                    }else{
                                        String json = result.getResult();
                                        if(json == null) json = "";
                                        T t = parseThreade(json);
                                        callBackApi.onComplete(t);
                                    }
                                    break;
                                default:
                                    String error = "Error SERVER " + result.getHeaders().code();
                                    Log.e(error);
                                    callBackApi.onException(new RequestException(error));
                                    break;
                            }
                        }else{
                            String error = "Unknown error";
                            Log.e(error);
                            callBackApi.onException(new RequestException(error));
                        }
                    }
                });
    }

    protected void baseRequestPOST(final String url, List<NameValuePair> sendData, final ICallBackTApi<T> callBackApi){
        Log.d("url reques", url);
        Builders.Any.B ion = null;
        if(mActivity != null){
            ion = Ion.with(mActivity).load(url);
        }else{
            ion = Ion.with(mContext).load(url);
        }

        for (int i = 0; i < sendData.size(); i++) {
            Log.d(sendData.get(i).getName(), sendData.get(i).getValue());
            ion.setMultipartParameter(sendData.get(i).getName(), sendData.get(i).getValue());
        }
        ion.asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(callBackApi == null) return;
                        if(isException(url, e, callBackApi)) return;
                        if (result != null) {
                            switch (result.getHeaders().code()){
                                case 200:
                                    Log.d("header code", 200);
                                    if(mActivity != null) {
                                        new Thread(new ParseRunnable(url, callBackApi, result.getResult(), "")).start();
                                    }else{
                                        String json = result.getResult();
                                        if(json == null) json = "";
                                        T t = parseThreade(json);
                                        callBackApi.onComplete(t);
                                    }
                                    break;
                                default:
                                    String error = "Error SERVER " + result.getHeaders().code();
                                    Log.e(error);
                                    callBackApi.onException(new RequestException(error));
                                    break;
                            }
                        }else{
                            String error = "Unknown error";
                            Log.e(error);
                            callBackApi.onException(new RequestException(error));
                        }
                    }
                });
    }

    private boolean isException(String url, Exception e, ICallBackTApi<T> callBackApi){
        if (e != null) {
            if(e.getClass() == UnknownHostException.class){
                callBackApi.onException(new RequestException(e.getMessage(), true, false));
                return true;
            }
            if(e.getClass() == TimeoutException.class){
                callBackApi.onException(new RequestException(e.getMessage(), false, true));
                return true;
            }
            callBackApi.onException(new RequestException(e.getMessage()));
            return true;
        }
        return false;
    }

    abstract protected T parseThreade(String resultJson);

    class ParseRunnable implements Runnable {

        private final ICallBackTApi<T> callBackApi;
        private final String time;
        String json;
        String url;

        public ParseRunnable(String url, ICallBackTApi<T> callBackApi, String json, String time) {
            this.url = url;
            this.json = json;
            this.callBackApi = callBackApi;
            this.time = time;
        }

        @Override
        public void run() {
            if(json == null) json = "";
            final T t = parseThreade(json);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBackApi.onComplete(t);
                }
            });
        }
    }
}
