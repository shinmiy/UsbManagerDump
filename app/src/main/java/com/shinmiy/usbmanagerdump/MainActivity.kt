package com.shinmiy.usbmanagerdump

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val usbManger: UsbManager by lazy { getSystemService(Context.USB_SERVICE) as UsbManager }

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.device_text)

        findViewById<Button>(R.id.scan_button).setOnClickListener {
            textView.text = usbManger.deviceList.toPrettyString()
        }
    }
}

private fun HashMap<String, UsbDevice>.toPrettyString() = buildString {
    if (this@toPrettyString.isEmpty()) {
        append("No devices found.")
        return@buildString
    }

    this@toPrettyString.forEach { (name, device) ->
        appendln("===================================")
        appendln("name            : $name")
        appendln("deviceId        : ${device.deviceId}")
        appendln("vendorId        : ${device.vendorId}")
        appendln("productId       : ${device.productId}")
        appendln("deviceClass     : ${device.deviceClass}")
        appendln("deviceSubclass  : ${device.deviceSubclass}")
        appendln("deviceProtocol  : ${device.deviceProtocol}")
        appendln("manufacturerName: ${device.manufacturerName}")
        appendln("productName     : ${device.productName}")
        appendln("version         : ${device.version}")
        appendln("------------------------------------")
        repeat(device.configurationCount) { i ->
            val config = device.getConfiguration(i)
            appendln(config.toString())
        }
        appendln("===================================")
    }
}