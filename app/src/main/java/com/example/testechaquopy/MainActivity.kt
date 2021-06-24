package com.example.testechaquopy

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.testechaquopy.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        val TAG = "testechaquopy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onResume() {
        super.onResume()
        requisitarPermissoes()
        verificarDiretorio()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                testePython()
                true
            }
            R.id.action_database -> {
                criarDatabase()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /**
     * @see https://chaquo.com/chaquopy/doc/current/python.html
     * @see https://stackoverflow.com/questions/57232024/running-files-from-externalstorage-on-chaquopy
     * @see https://github.com/chaquo/chaquopy-matplotlib/blob/master/app/src/main/java/com/chaquo/myapplication/MainActivity.kt
     */
    fun testePython() {
        try {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(applicationContext))
                val py = Python.getInstance()

                val opcao = 4

                if (opcao == 0) {
                    val mdesconto = py.getModule("regras")
                    val valor = mdesconto.callAttr("desconto", 50).toInt()
                    Log.d(TAG, valor.toString())

                }
                if (opcao == 1) {
                    val fdRegras = File(
                        applicationContext.getExternalFilesDir("regras").toString(),
                        "regras2.py"
                    )
                    if (fdRegras.exists()) {
                        py.getModule("sys").get("path")?.callAttr(
                            "append",
                            applicationContext.getExternalFilesDir("regras").toString()
                        )
                        val mdesconto = py.getModule("regras2")
                        if (mdesconto != null) {
                            val valor = mdesconto.callAttr("desconto", 50).toInt()
                            Log.d(TAG, valor.toString())
                        }
                    } else {
                        throw java.lang.Exception("Arquivo de regras não existe")
                    }
                }
                if (opcao == 3) {
                    val mdesconto = py.getModule("regras")
                    mdesconto.put("gui_getdesconto", ::getDesconto)
                    val valor = mdesconto.callAttr("preco", 50).toInt()
                    Log.d(TAG, valor.toString())
                }
                if (opcao == 4) {
                    val fdRegras = File(
                        applicationContext.getExternalFilesDir("regras").toString(),
                        "regras2.py"
                    )
                    if (fdRegras.exists()) {
                        py.getModule("sys").get("path")?.callAttr(
                            "append",
                            applicationContext.getExternalFilesDir("regras").toString()
                        )
                        val mdesconto = py.getModule("regras2")
                        if (mdesconto != null) {
                            mdesconto.put("gui_getdesconto", ::getDesconto)
                            val valor = mdesconto.callAttr("preco", 50).toInt()
                            Log.d(TAG, valor.toString())
                        } else {
                            throw java.lang.Exception("Arquivo de regras não existe")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    fun getDesconto(codigo: Int): Int {
        var produtoDAO = ProdutoDAO(applicationContext, null)
        var produto = produtoDAO.recuperar(codigo)
        Log.d(TAG, "getDesconto ${codigo}")
        Log.d(
            TAG,
            "Produto: ${produto.codigo} ${produto.nome} ${produto.preco} ${produto.desconto}"
        )
        return produto.desconto
    }

    fun requisitarPermissoes() {
        PermissionUtils.requestForPermission(this)
    }

    fun verificarDiretorio() {
        val fdRegras = File(applicationContext.getExternalFilesDir("regras").toString())
        Log.d(TAG, fdRegras.absolutePath)
        if (fdRegras.exists() && fdRegras.isDirectory) {
            Log.d(TAG, "Dir regras criado!")
        } else {
            Log.e(TAG, "Dir regras inexistente!")
        }
    }

    fun criarDatabase() {
        try {

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

}