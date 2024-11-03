package com.github.cpjinan.plugin.akaricdk.internal.command.subCommand

import com.github.cpjinan.plugin.akaricdk.internal.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.util.*

object CodeCommand {
    val code = subCommand {
        createHelper()
        literal("create") {
            literal("randomCode").dynamic("kit").dynamic("isServerWide").dynamic("amount") {
                execute<ProxyCommandSender> { sender, context, _ ->
                    val randomList = List(context["amount"].toInt()) { UUID.randomUUID().toString() }
                    val codeList = randomList.forEach {
                        ConfigManager.getCodeList().toMutableList().apply {
                            add("${it}|${context["kit"]}|${context["isServerWide"]}")
                        }
                    }
                    ConfigManager.code["Redeem-Code"] = codeList
                    ConfigManager.code.saveToFile()
                    sender.sendLang("Create-Code", context["code"])
                }
            }
            dynamic("code").dynamic("kit").dynamic("isServerWide") {
                execute<ProxyCommandSender> { sender, context, _ ->
                    val codeList = ConfigManager.getCodeList().toMutableList().apply {
                        add("${context["code"]}|${context["kit"]}|${context["isServerWide"]}")
                    }
                    ConfigManager.code["Redeem-Code"] = codeList
                    ConfigManager.code.saveToFile()
                    sender.sendLang("Create-Code", context["code"])
                }
            }
        }
        literal("delete") {
            dynamic("code") {
                execute<ProxyCommandSender> { sender, context, _ ->
                    val codeList = ConfigManager.getCodeList().toMutableList()
                    codeList.removeIf { it.startsWith("${context["code"]}|") }
                    ConfigManager.code["Redeem-Code"] = codeList
                    ConfigManager.code.saveToFile()
                    sender.sendLang("Delete-Code", context["code"])
                }
            }
        }
    }
}
