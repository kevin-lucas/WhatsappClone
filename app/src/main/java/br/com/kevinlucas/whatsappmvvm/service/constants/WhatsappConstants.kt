package br.com.kevinlucas.whatsappmvvm.service.constants

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object WhatsappConstants {

    const val REQUEST_CODE_SEND_SMS_PERMISSION = 0

    // SharedPreferences
    object SHARED {
        const val TOKEN_KEY = "tokenkey"
        const val TOKEN_KEY_TEMP = "tokentemp"
        const val PERSON_KEY = "personkey"
        const val PERSON_NAME = "personname"
        const val PERSON_EMAIL = "personemail"
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