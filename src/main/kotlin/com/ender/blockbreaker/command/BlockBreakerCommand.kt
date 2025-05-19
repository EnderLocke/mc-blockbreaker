package com.ender.blockbreaker.command

import com.ender.blockbreaker.BlockBreakerConfig
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

fun registerBlockBreakerCommand(): LiteralArgumentBuilder<ServerCommandSource> {
    return CommandManager.literal("blockbreaker")
            .executes { context ->
                BlockBreakerConfig.enabled = !BlockBreakerConfig.enabled
                val status = if (BlockBreakerConfig.enabled) "enabled" else "disabled"
                context.source.sendMessage(Text.literal("BlockBreaker is now $status."))
                1
            }
}