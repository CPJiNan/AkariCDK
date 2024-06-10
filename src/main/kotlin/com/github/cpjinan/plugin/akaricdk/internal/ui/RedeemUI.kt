package com.github.cpjinan.plugin.akaricdk.internal.ui

import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.module.lang.asLangText
import taboolib.module.lang.asLangTextList
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Anvil
import taboolib.platform.util.inputBook
import taboolib.platform.util.nextChatInTick
import taboolib.platform.util.sendLang

object RedeemUI {
    fun Player.getBookInput(): String {
        var input = ""
        val display = console().asLangText("UI-Display")
        val content = console().asLangTextList("UI-Book").joinToString()
        this.inputBook(display, true, listOf("", content)) { book ->
            input = book.firstOrNull().orEmpty()
        }
        return input
    }

    fun Player.getChatInput(): String {
        var input = ""
        this.sendLang("UI-Chat")
        this.nextChatInTick(20 * 30, {
            input = it
        }, {
            this.sendLang("UI-Timeout")
        }, {
            this.sendLang("UI-Canceled")
        })
        return input
    }

    fun Player.getAnvilInput() : String {
        var input = ""
        val display = console().asLangText("UI-Display")
        this.openMenu<Anvil>(display){
            onRename { _, name, _ ->
                input = name
            }
        }
        return input
    }
}