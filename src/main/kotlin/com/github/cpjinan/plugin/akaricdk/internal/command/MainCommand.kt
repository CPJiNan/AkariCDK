package com.github.cpjinan.plugin.akaricdk.internal.command

import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.DatabaseCommand
import com.github.cpjinan.plugin.akaricdk.internal.command.subCommand.RedeemCommand
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "akaricdk", aliases = ["cdk"], permission = "akaricdk.default", permissionDefault = PermissionDefault.TRUE)
object MainCommand {
    @CommandBody(permission = "akaricdk.default", permissionDefault = PermissionDefault.TRUE)
    val main = mainCommand { createHelper() }

    @CommandBody(permission = "akaricdk.admin")
    val database = DatabaseCommand.database

    @CommandBody(permission = "akaricdk.default")
    val redeem = RedeemCommand.redeem
}