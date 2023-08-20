package com.asustug.clientaidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.asustug.ServerAIDL.IMathAidlInterface
import com.asustug.clientaidl.ui.theme.ClientAIDLTheme


class MainActivity : ComponentActivity() {

    private lateinit var iMathAIDL: IMathAidlInterface
    val TAG: String = MainActivity::class.java.name
    val package_Name: String = "com.asustug.ServerAIDL"
    val class_Name: String = "com.asustug.ServerAIDL.AIDLServerService"

    private final val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            iMathAIDL = IMathAidlInterface.Stub.asInterface(p1)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "Remote config Service Not-Connected!!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientAIDLTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val intent = Intent("connect_to_aidl_service")
                    intent.setClassName(package_Name, class_Name)
                    if (bindService(intent, serviceConnection, BIND_AUTO_CREATE)) {
                        Toast.makeText(context, "Bind service Succeeded", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Bind service failed", Toast.LENGTH_SHORT).show()
                    }
                    Text(text = "Hello!", modifier = Modifier.clickable {
                        Log.d(TAG, "onCreate: "+ iMathAIDL.add(10,20))
                    })
                }
            }
        }
    }


    override fun onStop() {
        unbindService(serviceConnection)
        super.onStop()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClientAIDLTheme {
        Greeting("Android")
    }
}