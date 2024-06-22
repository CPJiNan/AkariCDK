package com.github.cpjinan.plugin.akaricdk

import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object AkariCDK : Plugin() {
    val plugin by lazy { BukkitPlugin.getInstance() }
}