package com.elanyudho.testsdt.ui.firstpart

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
@AndroidEntryPoint
class BackgroundService : Service() {

    companion object {
        internal val TAG = BackgroundService::class.java.simpleName
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
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

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: Service Stopped")
    }
}