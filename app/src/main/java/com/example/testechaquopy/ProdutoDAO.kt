package com.example.testechaquopy

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ProdutoDAO(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
    ) :  DatabaseBasic(context = context, factory = factory){
    companion object {
        private val TAG = ProdutoDAO::class.java.simpleName
        val TABELA = "produto"
        val CODIGO = "codigo"
        val NOME = "nome"
        val PRECO = "preco"
        val DESCONTO = "desconto"
    }

    fun recuperar(codigo: Int) : Produto {
        var db : SQLiteDatabase? = null
        var retorno = Produto()
        try {
            db = this.readableDatabase
            val columns = arrayOf(
                CODIGO,
                NOME,
                PRECO,
                DESCONTO
            )
            var where = "${CODIGO} = ?"
            var whereArgs = arrayListOf(codigo.toString())
            var cursor: Cursor? = null
            try {
                cursor =
                    db.query(
                        TABELA,
                        columns,
                        where,
                        whereArgs.toTypedArray(),
                        null,
                        null,
                        null,
                        "1"
                    )
                cursor!!.moveToFirst()
                if (cursor.count > 0) {
                    retorno.codigo = cursor.getInt(cursor.getColumnIndex(CODIGO))
                    retorno.nome = cursor.getString(cursor.getColumnIndex(NOME))
                    retorno.preco = cursor.getInt(cursor.getColumnIndex(PRECO))
                    retorno.desconto = cursor.getInt(cursor.getColumnIndex(DESCONTO))
                }
            } finally {
                cursor?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db?.close()
        }

        return retorno
    }

}