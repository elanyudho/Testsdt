package com.elanyudho.testsdt.ui.firstpart

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.elanyudho.testsdt.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class BackgroundService : Service() {

    private var onItemClickCallback: OnItemClickCallback? = null

    private val mBinder: IBinder = LocalBinder()

    //returns the instance of the service
    inner class LocalBinder : Binder() {
        fun getService() : BackgroundService {
            return this@BackgroundService
        }
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Views
    private lateinit var mButtonView: View
    private lateinit var mButton: Button

    private val listName = listOf<String>("Luffy", "Nami", "Zoro", "Usop", "Sanji", "Chopper", "Robin")

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mButtonView = layoutInflater.inflate(R.layout.layout_window_manager, null)

        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )

        mButton = mButtonView.findViewById(R.id.btn_send_string) as Button

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(mButtonView, params)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service is running", Toast.LENGTH_SHORT).show()

        mButton.setOnClickListener {
            val randomInt = (0..6).random()
            onItemClickCallback?.onItemClicked(listName[randomInt])
        }

        serviceScope.launch {
            for (i in 1..10) {
                delay(1000)
                Log.d(TAG, "Do Something $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.removeView(mButtonView)
        Log.d(TAG, "onDestroy: Service Stopped")
    }

    companion object {
        internal val TAG = BackgroundService::class.java.simpleName
    }

}