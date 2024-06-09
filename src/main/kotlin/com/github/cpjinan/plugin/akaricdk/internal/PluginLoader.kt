package com.github.cpjinan.plugin.akaricdk.internal

import com.github.cpjinan.plugin.akaricdk.AkariCDK.plugin
import com.github.cpjinan.plugin.akaricdk.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akaricdk.utils.LoggerUtil
import com.github.cpjinan.plugin.akaricdk.utils.UpdateUtil
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.function.console
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics

object PluginLoader {
    @Awake(LifeCycle.LOAD)
    fun load() {
        console().sendLang("Plugin-Loading", plugin.description.version)
        Metrics(18992, plugin.description.version, Platform.BUKKIT)
    }

    @Awake(LifeCycle.ENABLE)
    fun enable() {
        LoggerUtil.message(
            "",
            "&o      _    _              _  ____ ____  _  __ ".colored(),
            "&o     / \\  | | ____ _ _ __(_)/ ___|  _ \\| |/ / ".colored(),
            "&o    / _ \\ | |/ / _` | '__| | |   | | | | ' /  ".colored(),
            "&o   / ___ \\|   < (_| | |  | | |___| |_| | . \\  ".colored(),
            "&o  /_/   \\_\\_|\\_\\__,_|_|  |_|\\____|____/|_|\\_\\ ".colored(),
            ""
        )
        console().sendLang("Plugin-Enabled")
        UpdateUtil.getPluginNotice()
        UpdateUtil.getPluginUpdate()
        UpdateUtil.getConfigUpdate()
    }

    @Awake(LifeCycle.DISABLE)
    fun disable() {
        DatabaseManager.getDatabase().save()
        console().sendLang("Plugin-Disable")
    }

}