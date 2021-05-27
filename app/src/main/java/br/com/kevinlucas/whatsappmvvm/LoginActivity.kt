package br.com.kevinlucas.whatsappmvvm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.mContext = this
        this.mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // setMaskFormatter
        setMaskFormatter()

        // Inicializa eventos
        setListeners();
        observe()

        requestPermissions()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_login) {
            handleLogin()
        }
    }

    private fun setListeners() {
        button_login.setOnClickListener(this)
    }

    private fun observe() {
        mViewModel.login().observe(this, Observer {
            if (it) {
                val intent = Intent(this, ValidatorActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Problema ao enviar SMS, tente novamente!!", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun setMaskFormatter(){

        var smf = SimpleMaskFormatter("+NN")
        val mtwCode = MaskTextWatcher(edit_code, smf)
        edit_code.addTextChangedListener(mtwCode)

        smf = SimpleMaskFormatter("NN")
        val mtwArea = MaskTextWatcher(edit_area, smf)
        edit_area.addTextChangedListener(mtwArea)

        smf = SimpleMaskFormatter("NNNNN-NNNN")
        val mtwPhone = MaskTextWatcher(edit_phone, smf)
        edit_phone.addTextChangedListener(mtwPhone)
    }

    private fun handleLogin() {
        val phone = "5554"
        mViewModel.doLogin(phone)
    }

    private fun requestPermissions() {
        if (Constants.hasSMSPermissions(mContext)) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need accept send sms permissions to use this app.",
                Constants.REQUEST_CODE_SEND_SMS_PERMISSION,
                Manifest.permission.INTERNET
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need accept send sms permissions to use this app.",
                Constants.REQUEST_CODE_SEND_SMS_PERMISSION,
                Manifest.permission.SEND_SMS
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}