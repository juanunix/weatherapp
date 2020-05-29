package com.example.weatherapp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class Utils {
    private val progressDialog: ProgressDialog? = null
    private val context: Activity? = null
    private val clase_: Class<*>? = null
    fun showMessageOKCancel(message: String?, context: Context?, tituloOk: String?, tituloNo: String?,
                            okListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(context!!)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(tituloOk, okListener)
                .setNegativeButton(tituloNo, null)
                .create()
                .show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}