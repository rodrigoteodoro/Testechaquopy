package com.example.testechaquopy

import android.app.Service
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.File

fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

fun isExternalStorageReadable(): Boolean {
    return Environment.getExternalStorageState() in
            setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
}

/**
 * Retorna se tem acesso a armazenamento do dispositivo
 * @param context Service
 * @param Boolean
 */
fun isExternalStorageReady(context : Service): Boolean {

    var retorno = false

    if (isExternalStorageWritable() && isExternalStorageReadable()) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var codeW = context.getPackageManager().checkPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                context.getPackageName()
            )
            var codeR = context.getPackageManager().checkPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                context.getPackageName()
            )

            if (codeW == PackageManager.PERMISSION_GRANTED && codeR == PackageManager.PERMISSION_GRANTED) {
                retorno = true
            }

        }  else {
            retorno = true
        }
    }

    return retorno

}

fun listarArquivos(root: File): ArrayList<File> {
    val fileList: ArrayList<File> = ArrayList()
    val listAllFiles = root.listFiles()

    if (listAllFiles != null && listAllFiles.size > 0) {
        for (currentFile in listAllFiles) {
            fileList.add(currentFile.absoluteFile)
        }
    }
    return fileList
}