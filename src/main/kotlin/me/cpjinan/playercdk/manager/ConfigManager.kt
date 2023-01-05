package me.cpjinan.playercdk.manager

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

object ConfigManager {
    private const val configFile = "config.yml"
    private const val codeFile = "code.yml"
    private const val kitFile = "kit.yml"

    @Config(configFile, autoReload = true)
    lateinit var config : Configuration
    @ConfigNode("options", configFile)
    lateinit var options: ConfigurationSection
    @ConfigNode("module.CDK", configFile)
    lateinit var CDK: ConfigurationSection

    @Config(codeFile, autoReload = true)
    lateinit var code : Configuration

    @Config(kitFile, autoReload = true)
    lateinit var kit : Configuration
}