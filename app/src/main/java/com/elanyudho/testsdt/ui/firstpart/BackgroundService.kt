package com.elanyudho.testsdt.ui.firstpart

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.elanyudho.testsdt.R
import com.elanyudho.testsdt.domain.model.Jokes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class BackgroundService : Service() {

    companion object {
        internal val TAG = BackgroundService::class.java.simpleName
    }

    private lateinit var onClick: (data: String) -> Unit

    private val mLayoutParamsFlags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
    // Views
    private lateinit var mButtonView: View
    private lateinit var mButton: Button

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mButtonView = layoutInflater.inflate(R.layout.layout_window_manager, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            mLayoutParamsFlags,
            PixelFormat.TRANSLUCENT
        )

        mButton = mButtonView.findViewById(R.id.btn_send_string) as Button
        mButton.setOnClickListener { onClick.invoke("Hello World") }

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(mButtonView, params)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service is running", Toast.LENGTH_SHORT).show()
        serviceScope.launch {
            for (i in 1..10) {
                delay(1000)
                Log.d(TAG, "Do Something $i")
            }
            stopSelf()
            Toast.makeText(this@BackgroundService, "Service is stopped", Toast.LENGTH_SHORT).show()
        }
        return START_STICKY
    }

    fun setOnClickData(onClick: (data: String) -> Unit) {
        this.onClick = onClick
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.removeView(mButtonView)
        Log.d(TAG, "onDestroy: Service Stopped")
    }

}