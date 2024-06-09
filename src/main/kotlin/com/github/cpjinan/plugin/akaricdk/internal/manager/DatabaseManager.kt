package com.github.cpjinan.plugin.akaricdk.internal.manager

import com.github.cpjinan.plugin.akaricdk.internal.database.Database
import com.github.cpjinan.plugin.akaricdk.internal.database.DbCbor
import com.github.cpjinan.plugin.akaricdk.internal.database.DbJson
import com.github.cpjinan.plugin.akaricdk.internal.database.DbSql

object DatabaseManager {
    private var database: Database? = null

    private fun openDatabase(): Database {
        val dbType = ConfigManager.getMethod() ?: "JSON"
        return when (dbType) {
            "JSON" -> {
                DbJson()
            }

            "CBOR" -> {
                DbCbor()
            }

            "SQL" -> {
                DbSql()
            }

            else -> {
                throw IllegalArgumentException("Unknown dbType.")
            }
        }
    }

    fun getDatabase(): Database = if (database != null) {
        database!!
    } else {
        database = openDatabase()
        database!!
    }
}