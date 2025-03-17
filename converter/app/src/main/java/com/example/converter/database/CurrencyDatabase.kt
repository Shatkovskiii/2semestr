package com.example.converter.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CurrencyDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CurrencyDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_RATES = "rates"
        private const val COLUMN_CURRENCY = "currency"
        private const val COLUMN_RATE = "rate"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_RATES (
                $COLUMN_CURRENCY TEXT PRIMARY KEY,
                $COLUMN_RATE REAL NOT NULL
            )
        """.trimIndent()
        
        db.execSQL(createTable)

        // Добавляем начальные курсы валют
        val initialRates = listOf(
            "RUB" to 1.0,
            "USD" to 0.011,
            "EUR" to 0.01
        )

        initialRates.forEach { (currency, rate) ->
            db.insert(TABLE_RATES, null, android.content.ContentValues().apply {
                put(COLUMN_CURRENCY, currency)
                put(COLUMN_RATE, rate)
            })
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RATES")
        onCreate(db)
    }

    fun getRate(currency: String): Double {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_RATES,
            arrayOf(COLUMN_RATE),
            "$COLUMN_CURRENCY = ?",
            arrayOf(currency),
            null,
            null,
            null
        )
        
        return if (cursor.moveToFirst()) {
            cursor.getDouble(0)
        } else {
            1.0
        }
    }

    fun updateRate(currency: String, rate: Double) {
        val db = this.writableDatabase
        val values = android.content.ContentValues().apply {
            put(COLUMN_RATE, rate)
        }
        db.update(TABLE_RATES, values, "$COLUMN_CURRENCY = ?", arrayOf(currency))
    }
} 