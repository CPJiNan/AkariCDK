package com.github.cpjinan.plugin.akaricdk.internal.command

import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.DatabaseCommand
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "akaricdk", aliases = ["cdk"], permissionDefault = PermissionDefault.OP)
object MainCommand {
    @CommandBody
    val main = mainCommand { createHelper() }

    @CommandBody
    val database = DatabaseCommand.database
}