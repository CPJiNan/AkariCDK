package com.github.cpjinan.plugin.akaricdk.internal.command.subCommand

import com.github.cpjinan.plugin.akaricdk.internal.manager.DatabaseManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang

object DatabaseCommand {
    val database = subCommand {
        createHelper()
        literal("get") {
            dynamic("table").dynamic("index").dynamic("key") {
                execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                    sender.sendLang(
                        "Database-Get-Value",
                        context["table"],
                        context["index"],
                        context["key"],
                        DatabaseManager.getDatabase().getValue(context["table"], context["index"], context["key"])
                    )
                }
            }
        }
        literal("set") {
            dynamic("table").dynamic("index").dynamic("key").dynamic("value") {
                execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                    DatabaseManager.getDatabase()
                        .setValue(context["table"], context["index"], context["key"], context["value"])
                    sender.sendLang(
                        "Database-Set-Value",
                        context["table"],
                        context["index"],
                        context["key"],
                        context["value"]
                    )
                }
            }
        }
        literal("save") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, _: CommandContext<ProxyCommandSender>, _: String ->
                DatabaseManager.getDatabase().save()
                sender.sendLang("Database-Save")
            }
        }
    }
}