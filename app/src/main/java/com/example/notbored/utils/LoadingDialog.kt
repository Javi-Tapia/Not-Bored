package com.example.notbored.utils

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import com.example.notbored.R
import kotlin.math.log

class LoadingDialog (myActivity: Activity){

    var activity: Activity = myActivity
    lateinit var dialog: AlertDialog

    fun startLoadingDialog(){

        Log.i("MESSAGE", "ENTRANDOOO")
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog,null))
        builder.setCancelable(false)
        dialog  = builder.create()
        dialog.show()

    }

    fun  dismissDialog() {
        Log.i("MESSAGE", "SALIENDO")
        dialog.dismiss()
        dialog.cancel()

    }


}