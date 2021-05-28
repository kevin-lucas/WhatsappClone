package br.com.kevinlucas.whatsappmvvm.service.constants

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object WhatsappConstants {

    const val REQUEST_CODE_SEND_SMS_PERMISSION = 0

    // SharedPreferences
    object SHARED {
        const val TOKEN_KEY = "token"
        const val TOKEN_KEY_TEMP = "tokentemp"
    }

    fun hasSMSPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
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