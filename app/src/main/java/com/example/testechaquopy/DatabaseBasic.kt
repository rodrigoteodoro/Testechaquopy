package com.example.testechaquopy

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseBasic(context: Context,
                     factory: SQLiteDatabase.CursorFactory?
)  : SQLiteOpenHelper(
    context, DatabaseConfiguration.DATABASE_NAME,
    factory, DatabaseConfiguration.DATABASE_VERSION
) {

    companion object {
        private val TAG = DatabaseBasic::class.java.simpleName
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = ("CREATE TABLE " + ProdutoDAO.TABELA + "("
                + ProdutoDAO.CODIGO + " integer primary key autoincrement,"
                + ProdutoDAO.NOME + " text,"
                + ProdutoDAO.PRECO + " integer,"
                + ProdutoDAO.DESCONTO + " integer"
                + ")")
        db!!.execSQL(sql)
        val iterator = (1..3).iterator()
        iterator.forEach {
            sql =
                "INSERT INTO ${ProdutoDAO.TABELA} (${ProdutoDAO.NOME}, ${ProdutoDAO.PRECO}, ${ProdutoDAO.DESCONTO}) VALUES('Produto $it', 10, 5);"
            db!!.execSQL(sql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ProdutoDAO.TABELA}")
        onCreate(db)
    }
}