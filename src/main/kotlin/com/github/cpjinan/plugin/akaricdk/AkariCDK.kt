package com.github.cpjinan.plugin.akaricdk

import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

@RuntimeDependencies(
    RuntimeDependency(
        value = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2",
        relocate = ["!kotlin.", "!kotlin1922."]
    ),
    RuntimeDependency(
        value = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2",
        relocate = ["!kotlin.", "!kotlin1922."]
    ),
    RuntimeDependency(
        value = "org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.6.2",
        relocate = ["!kotlin.", "!kotlin1922."]
    )
)
object AkariCDK : Plugin() {
    val plugin by lazy { BukkitPlugin.getInstance() }
}