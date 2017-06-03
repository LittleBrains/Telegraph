package ru.littlebrains.telegraph

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast

import ru.littlebrains.telegraph.api.CreateAccountApi
import ru.littlebrains.telegraph.api.CreatePageApi
import ru.littlebrains.telegraph.api.ICallBackTApi
import ru.littlebrains.telegraph.api.RequestException
import ru.littlebrains.telegraph.fragment.MainFragment
import ru.littlebrains.telegraph.model.AccountModel
import ru.littlebrains.telegraph.model.PageModel
import trikita.log.Log

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setDisplayHomeAsUp()

        val token = Utils.getSharedPreferences(baseContext).getString("token", null);
        if(token != null){
            Log.d("token", token);

            newFragment(MainFragment(), R.layout.fragment_main, false);

            /*CreatePageApi(this).requestGet(token, "my 5 page", object : ICallBackTApi<PageModel>{
                override fun onComplete(result: PageModel) {
                }
                override fun onException(e: RequestException) {
                    Log.d("CreatePageApi", e)
                }
            })*/
        }else {
            CreateAccountApi(this).requestGet("little", "lb", "vk.com/kizirove", object : ICallBackTApi<AccountModel> {
                override fun onComplete(result: AccountModel) {
                    Log.d("result token", result.ok, result.access_token);
                    if(result.ok) {
                        Utils.getSharedPreferencesEditor(baseContext).putString("token", result.access_token).commit();
                        newFragment(MainFragment(), R.layout.fragment_main, false);
                    }else{
                        Toast.makeText(baseContext, "error ok", Toast.LENGTH_SHORT)
                    }
                }

                override fun onException(e: RequestException) {
                    Toast.makeText(baseContext, "error", Toast.LENGTH_SHORT)
                }
            })
        }
    }
}
