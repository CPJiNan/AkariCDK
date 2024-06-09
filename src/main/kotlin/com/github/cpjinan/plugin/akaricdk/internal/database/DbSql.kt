package com.github.cpjinan.plugin.akaricdk.internal.database

import com.github.cpjinan.plugin.akaricdk.internal.manager.ConfigManager
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table

class DbSql : Database {
    private val host = ConfigManager.getSqlHost()
    private val dataSource by lazy { host.createDataSource() }
    private val sqlTable = Table(ConfigManager.getSqlTable(), host) {
        add("table") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("index") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("key") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("value") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
    }

    init {
        sqlTable.createTable(dataSource)
    }

    override fun getValue(table: String, index: String, key: String): String {
        return get(table, index, key)
    }

    override fun setValue(table: String, index: String, key: String, value: String) {
        set(table, index, key, value)
    }

    override fun save() {}

    private fun add(table: String, index: String, key: String, value: String) {
        sqlTable.insert(dataSource, "table", "index", "key", "value") {
            value(table, index, key, value.orEmpty())
        }
    }

    private fun delete(table: String, index: String, key: String) {
        sqlTable.delete(dataSource) {
            where { "table" eq table and ("index" eq index) and ("key" eq key) }
        }
    }

    fun set(table: String, index: String, key: String, value: String) {
        if (have(table, index, key)) sqlTable.update(dataSource) {
            set("value", value)
            where("table" eq table and ("index" eq index) and ("key" eq key))
        } else add(table, index, key, value)
    }

    fun get(table: String, index: String, key: String): String {
        return sqlTable.select(dataSource) {
            where("table" eq table and ("index" eq index) and ("key" eq key))
            limit(1)
        }.firstOrNull {
            this.getString("value")
        }.orEmpty()
    }

    fun have(table: String, index: String, key: String): Boolean {
        return sqlTable.select(dataSource) {
            where("table" eq table and ("index" eq index) and ("key" eq key))
            limit(1)
        }.firstOrNull {
            true
        } ?: false
    }
}