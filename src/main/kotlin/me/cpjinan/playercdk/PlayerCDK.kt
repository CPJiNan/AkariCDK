package me.cpjinan.playercdk

import me.cpjinan.playercdk.manager.RegisterManager.registerAll
import taboolib.common.platform.Plugin

object PlayerCDK : Plugin() {
    override fun onEnable() {
        registerAll()
    }
}