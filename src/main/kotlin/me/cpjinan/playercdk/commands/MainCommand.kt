package me.cpjinan.playercdk.commands

import me.cpjinan.playercdk.manager.ConfigManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.expansion.getDataContainer
import taboolib.expansion.setupDataContainer
import taboolib.module.chat.colored
import taboolib.module.kether.KetherShell

@CommandHeader(name = "playercdk", aliases = ["cdk"], permission = "playercdk.default")
object MainCommand {

    @CommandBody(permission = "playercdk.default")
    val main = mainCommand {
        createHelper()
    }

    @CommandBody(permission = "playercdk.admin")
    val kit = KitCommand

    @CommandBody(permission = "playercdk.admin")
    val code = CodeCommand

    /**
     * playercdk exchange <code>
     */
    @CommandBody(permission = "playercdk.default")
    val exchange = subCommand {
        dynamic("code") {
            execute<ProxyCommandSender> { sender, context, _ ->
                Bukkit.getPlayerExact(sender.name)!!.setupDataContainer()

                val codeList : List<String> = ConfigManager.code.getStringList("code")

                var code : String ? = null
                val list : MutableList<String> = codeList.toMutableList()

                for (item in codeList) if (item.split(":")[0] == context.get(1)) code = item

                if (code == null) { sender.sendMessage("&c无效的兑换码!".colored()); return@execute }

                if (code.split(":")[2].toBoolean()) {
                    if (!Bukkit.getPlayerExact(sender.name)!!.getDataContainer()[code.split(":")[0]].toBoolean()) {
                        KetherShell.eval(ConfigManager.kit.getStringList("kit." + code.split(":")[1] + ".action"), sender = sender)
                        Bukkit.getPlayerExact(sender.name)!!.getDataContainer()[code.split(":")[0]] = true

                        return@execute
                    }

                    sender.sendMessage("&c兑换码已被使用!".colored())
                    return@execute
                }

                list.removeIf { it == code }
                ConfigManager.code["code"] = list.toList()
                ConfigManager.code.saveToFile(ConfigManager.code.file)
                KetherShell.eval(ConfigManager.kit.getStringList("kit." + code.split(":")[1] + ".action"), sender = sender)
            }
        }
    }

}
