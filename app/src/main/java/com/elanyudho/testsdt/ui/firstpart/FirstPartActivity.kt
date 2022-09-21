package com.elanyudho.testsdt.ui.firstpart

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.elanyudho.testsdt.databinding.ActivityFirstPartBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstPartActivity : AppCompatActivity(), OnItemClickCallback {

    private lateinit var binding: ActivityFirstPartBinding

    var myService: BackgroundService? = null

    private var dataFromService : MutableLiveData<String> = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this@FirstPartActivity, BackgroundService::class.java)

        binding.btnServiceOn.setOnClickListener {
            startService(intent)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }

        binding.btnServiceOff.setOnClickListener {
            if (isMyServiceRunning(BackgroundService::class.java)) {
                unbindService(mConnection)
                stopService(intent)
            } else {
                Toast.makeText(this@FirstPartActivity, "Service is still not running", Toast.LENGTH_SHORT).show()
            }

        }
        dataFromService.observe(this@FirstPartActivity
        ) {
            binding.tvMessage.text = it
        }

    }

    override fun onItemClicked(data: String) {
        dataFromService.value = data
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // Binded to LocalService, cast the IBinder and get LocalService instance
            val binder: BackgroundService.LocalBinder = service as BackgroundService.LocalBinder
            myService = binder.getService() //Get instance of your service!
            myService?.onItemClickCallback(this@FirstPartActivity)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
           return
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}