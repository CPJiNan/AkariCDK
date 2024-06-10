package com.github.cpjinan.plugin.akaricdk.internal.command.subCommand

import com.github.cpjinan.plugin.akaricdk.common.script.kether.KetherUtil.evalKether
import com.github.cpjinan.plugin.akaricdk.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akaricdk.internal.manager.DatabaseManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import taboolib.platform.util.nextChatInTick
import taboolib.platform.util.sendLang

object RedeemCommand {
    val redeem = subCommand {
        createHelper()
        dynamic("code", optional = true) {
            execute<ProxyCommandSender> { sender, context, _ ->
                val codeInput = context["code"]
                val codeList = ConfigManager.getCodeList()
                val code = codeList.find { it.startsWith("$codeInput|") }

                if (code == null) {
                    sender.sendLang("Redeem-Invalid-Code")
                    return@execute
                }

                val codeParts = code.split("|")
                val isReusable = codeParts[2].toBoolean()
                val codeKey = codeParts[0]
                val kitAction = ConfigManager.kit.getStringList("Kit.${codeParts[1]}.Action")

                if (isReusable) {
                    val hasUsedCode = DatabaseManager.getDatabase().getValue("Player", sender.name, codeKey).toBoolean()
                    if (!hasUsedCode) {
                        kitAction.evalKether(Bukkit.getPlayer(sender.name)!!)
                        DatabaseManager.getDatabase().setValue("Player", sender.name, codeKey, "true")
                        sender.sendLang("Redeem-Success")
                    } else {
                        sender.sendLang("Redeem-Code-Used")
                    }
                } else {
                    val updatedList = codeList.filterNot { it == code }
                    ConfigManager.code["Redeem-Code"] = updatedList
                    ConfigManager.code.saveToFile(ConfigManager.code.file)
                    kitAction.evalKether(Bukkit.getPlayer(sender.name)!!)
                    sender.sendLang("Redeem-Success")
                }
            }
        }
        execute { sender: ProxyCommandSender, _, _ ->
            val player = Bukkit.getPlayer(sender.name) ?: return@execute
            player.sendLang("Redeem-About")
            player.nextChatInTick(20 * 30, { input ->
                if (input.isEmpty()) sender.sendLang("Redeem-Canceled")
                else player.performCommand("akaricdk redeem $input")
            }, {
                player.sendLang("Redeem-Timeout")
            }, {
                player.sendLang("Redeem-Canceled")
            })
        }
    }
}

