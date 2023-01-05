package me.cpjinan.playercdk.manager

import taboolib.common.io.newFile
import taboolib.common.platform.Platform
import taboolib.common.platform.function.getDataFolder
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin

object RegisterManager {

    /**
     * 快捷注册方法
     */
    fun registerAll() {
        registerMetrics()
        registerDatabase()
    }

    /**
     * bStats统计注册方法
     */
    private fun registerMetrics() {
        if (ConfigManager.options.getBoolean("metrics")) Metrics(17292, BukkitPlugin.getInstance().description.version, Platform.BUKKIT)
    }

    /**
     * 数据库注册方法
     */
    private fun registerDatabase() {
        if (ConfigManager.options.getString("database.use").equals("LOCAL")) { setupPlayerDatabase(newFile(getDataFolder(), "database.db")); return }
        if (ConfigManager.options.getString("database.use").equals("SQL")) ConfigManager.options.getConfigurationSection("database.source.SQL")?.let { setupPlayerDatabase(it) }
    }

}