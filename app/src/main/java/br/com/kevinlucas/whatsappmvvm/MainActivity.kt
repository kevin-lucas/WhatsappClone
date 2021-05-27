package br.com.kevinlucas.whatsappmvvm

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.kevinlucas.whatsappmvvm.Constants.REQUEST_CODE_SEND_SMS_PERMISSION
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}