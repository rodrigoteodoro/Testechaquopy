package com.example.testechaquopy

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionUtils {

    val EXTERNAL_PERMS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    val EXTERNAL_REQUEST = 138;

    fun hasPermission(context: Context, perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, perm)

    }

    fun canAccessPermissions(context: Context): Boolean {
        if (!hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return false
        if (!hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE))
            return false
        if (!hasPermission(context, Manifest.permission.INTERNET))
            return false
        if (!hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION))
            return false
        if (!hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION))
            return false
        if (!hasPermission(context, Manifest.permission.ACCESS_WIFI_STATE))
            return false
        if (!hasPermission(context, Manifest.permission.READ_PHONE_STATE))
            return false
        if (!hasPermission(context, Manifest.permission.WAKE_LOCK))
            return false
        if (!hasPermission(context, Manifest.permission.CHANGE_WIFI_STATE))
            return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!hasPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION))
                return false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!hasPermission(context, Manifest.permission.FOREGROUND_SERVICE))
                return false
        }
        return true
    }

    /**
     * Verifica se as permissões são atendidas
     */
    fun requestForPermission(activity: Activity): Boolean {
        var isPermissionOn = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canAccessPermissions(activity)) {
                isPermissionOn = false
                activity.requestPermissions(
                    EXTERNAL_PERMS,
                    EXTERNAL_REQUEST
                )
            }
        }
        return isPermissionOn
    }



}