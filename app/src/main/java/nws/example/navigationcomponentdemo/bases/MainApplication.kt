package nws.example.navigationcomponentdemo.bases

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import nws.example.navigationcomponentdemo.BuildConfig
import timber.log.Timber

class MainApplication : Application() {

    companion object {
        private lateinit var mSelf: MainApplication

        fun self(): MainApplication {
            return mSelf
        }
    }

    private lateinit var mGson: Gson
    fun getGson(): Gson {
        return mGson
    }

    var isInitDone = false

    override fun onCreate() {
        super.onCreate()
        mSelf = this
        mGson = GsonBuilder().create()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}