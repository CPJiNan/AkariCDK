package com.github.cpjinan.plugin.akaricdk.internal.command

import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.CodeCommand
import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.DatabaseCommand
import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.RedeemCommand
import com.github.cpjinan.plugin.akaricdk.internal.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang

@CommandHeader(
    name = "akaricdk",
    aliases = ["cdk"],
    permission = "akaricdk.default",
    permissionDefault = PermissionDefault.TRUE
)
object MainCommand {
    @CommandBody(permission = "akaricdk.default", permissionDefault = PermissionDefault.TRUE)
    val main = mainCommand { createHelper() }

    @CommandBody(permission = "akaricdk.default", permissionDefault = PermissionDefault.TRUE)
    val help = mainCommand { createHelper() }

    @CommandBody(permission = "akaricdk.default", permissionDefault = PermissionDefault.TRUE)
    val redeem = RedeemCommand.redeem

    @CommandBody(permission = "akaricdk.admin")
    val code = CodeCommand.code

    @CommandBody(permission = "akaricdk.admin")
    val database = DatabaseCommand.database

    @CommandBody(permission = "akaricdk.admin")
    val reload = subCommand {
        execute { sender: ProxyCommandSender, _: CommandContext<ProxyCommandSender>, _: String ->
            ConfigManager.settings.reload()
            ConfigManager.code.reload()
            ConfigManager.kit.reload()
            sender.sendLang("Plugin-Reloaded")
        }
    }
}