package me.cpjinan.playercdk.commands

import me.cpjinan.playercdk.manager.ConfigManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import java.util.*

/**
 * playercdk code add fixed <kit> <code> <limit>
 * playercdk code add random <kit> <amount> <limit>
 */
@CommandHeader(name = "playercdkcode", permission = "playercdk.admin")
object CodeCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val add = subCommand {
        dynamic("type") {
            suggestion<CommandSender> { _, _ -> listOf("fixed", "random") }
            dynamic("kit") {
                dynamic("code") {
                    dynamic("limit") {
                        suggestion<CommandSender> { _, _ -> listOf("true", "false") }
                        execute<ProxyCommandSender> { sender, context, _ ->
                            if (context.get(2) == "fixed") {
                                val fixedCodeList: List<String> = ConfigManager.code.getStringList("code")
                                    .plus(context.get(4) + ":" + context.get(3) + ":" + context.get(5))

                                ConfigManager.code["code"] = fixedCodeList
                                ConfigManager.code.saveToFile(ConfigManager.code.file)

                                sender.sendMessage(("&a兑换码 &f" + context.get(4) + "&7→" + context.get(3) + " &a已创建!").colored())

                                return@execute
                            }

                            if (context.get(2) == "random") {
                                var randomCodeList: List<String> = ConfigManager.code.getStringList("code")
                                repeat(context.get(4).toInt()) { randomCodeList = randomCodeList
                                    .plus(UUID.randomUUID().toString() + ":" + context.get(3) + ":" + context.get(5)) }

                                ConfigManager.code["code"] = randomCodeList
                                ConfigManager.code.saveToFile(ConfigManager.code.file)

                                sender.sendMessage(("&a兑换码 &f随机生成&7*" + context.get(4) + " &a已创建!").colored())

                                return@execute
                            }
                        }
                    }
                }
            }
        }
    }
}