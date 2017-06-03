package ru.littlebrains.telegraph.fragment

import android.graphics.Color
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ru.littlebrains.telegraph.BaseActivity
import ru.littlebrains.telegraph.R
import ru.littlebrains.telegraph.R.id.toolbar
import trikita.log.Log

abstract class BaseFragment : Fragment() {
    protected var rootView: View? = null
    private var progress: View? = null
    private var orientation: Int = 0
    protected var mActivity: BaseActivity? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
        mActivity = activity as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var actionBar = (activity as AppCompatActivity).supportActionBar!!
        actionBar.show()
        toolbar = activity.findViewById(R.id.toolbar) as Toolbar

        return null
    }

    protected val isNoRotated: Boolean
        get() {
            if (orientation == activity.resources.configuration.orientation && rootView != null) {
                return true
            }
            orientation = activity.resources.configuration.orientation
            return false
        }


    protected fun setTitle(title: String) {
        toolbar?.title = title
    }

    protected fun setTitle(idRes: Int) {
        toolbar?.setTitle(idRes)
    }

    @JvmOverloads fun setProgressLayout(isVisible: Boolean, trancparent: Boolean = false) {
        if (isVisible) {
            progress = activity.layoutInflater.inflate(R.layout.progress_layout, null, false)
            if(progress != null) {
                (progress as View).setOnClickListener ( View.OnClickListener { v ->
                    Log.d(v.getTag())
                }
                )
                if (trancparent) {
                    (progress as View).setBackgroundColor(Color.parseColor("#60000000"))
                }
                (rootView as ViewGroup).addView(progress)
            }
        } else {
            (rootView as ViewGroup).removeView(progress)
        }
    }


    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity


}
