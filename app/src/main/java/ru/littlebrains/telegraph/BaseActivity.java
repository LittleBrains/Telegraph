package ru.littlebrains.telegraph;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import trikita.log.Log;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by evgeniy on 27.02.2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(backStackListener);
    }

    public void newFragment(Fragment fragment, int tag, boolean canGoBack) {
        newFragment(fragment, String.valueOf(tag), canGoBack);
    }

    public void newFragment(final Fragment fragment, final String tag, final boolean canGoBack) {
        final int WHAT = 1;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == WHAT) changeFragment(fragment, tag, canGoBack);
            }
        };
        handler.sendEmptyMessage(WHAT);

    }

    private void changeFragment(Fragment fragment, String tag, boolean canGoBack) {
        hideKeyboard();
        try {
            if (canGoBack) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, tag)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            } else {
                /*for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
                    getSupportFragmentManager().popBackStack();
                }*/
                clearBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, tag)
                        .commitAllowingStateLoss();
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
        }catch (IllegalStateException e){
            Log.d(e);
        }
    }

    public void clearBackStack(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        hideKeyboard();
    }

    public boolean backFragmet() {
        hideKeyboard();
        final int WHAT = 1;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                try {
                    if (msg.what == WHAT) getSupportFragmentManager().popBackStack();
                }catch(IllegalStateException e){

                }
            }
        };
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        handler.sendEmptyMessage(WHAT);

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            return false;
        }else {
            return true;
        }
    }

    private FragmentManager.OnBackStackChangedListener backStackListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            setDisplayHomeAsUp();
        };
    };

    protected void setDisplayHomeAsUp() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        getSupportActionBar().setDisplayHomeAsUpEnabled(backStackEntryCount != 0);
    }

    public void refreshMenu() {
        invalidateOptionsMenu();
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public boolean isVisibleFragment(int tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag+"");
        if(fragment != null && fragment.isVisible())return true;
        return false;
    }

    public Fragment getFragment(int tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag+"");
        if(fragment != null && fragment.isVisible())return fragment;
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            hideKeyboard();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
        }
    }


}
