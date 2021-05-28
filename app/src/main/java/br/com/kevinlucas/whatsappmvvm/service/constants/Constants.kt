package br.com.kevinlucas.whatsappmvvm.service.constants

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object Constants {

    const val REQUEST_CODE_SEND_SMS_PERMISSION = 0

    fun hasSMSPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.INTERNET
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.SEND_SMS,
                Manifest.permission.INTERNET
            )
        }
}