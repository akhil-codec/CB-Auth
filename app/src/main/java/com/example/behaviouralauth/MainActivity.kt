package com.example.behaviouralauth

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        findViewById<Button>(R.id.btnRequestAdmin).setOnClickListener {
            requestDeviceAdmin()
        }

        findViewById<Button>(R.id.btnTestLock).setOnClickListener {
            testLockNow()
        }

        findViewById<Button>(R.id.btnTouchCapture).setOnClickListener {
            startActivity(Intent(this, TouchCaptureActivity::class.java))
        }
    }

    private fun requestDeviceAdmin() {
        if (!devicePolicyManager.isAdminActive(adminComponent)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
                putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Needed to auto-lock the device when behavior drift is detected."
                )
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Admin already active", Toast.LENGTH_SHORT).show()
        }
    }

    private fun testLockNow() {
        if (devicePolicyManager.isAdminActive(adminComponent)) {
            devicePolicyManager.lockNow()
        } else {
            Toast.makeText(this, "Enable device admin first", Toast.LENGTH_SHORT).show()
        }
    }
}