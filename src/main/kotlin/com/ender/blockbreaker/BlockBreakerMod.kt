package com.ender.blockbreaker

import com.ender.blockbreaker.command.registerBlockBreakerCommand

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.IntProperty
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

class BlockBreakerMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("BlockBreaker")
    constructor()

    override fun onInitialize() {
        logger.info("BlockBreaker is initializing...")

        // Register break event handler
        PlayerBlockBreakEvents.BEFORE.register(PlayerBlockBreakEvents.Before { world, player, pos, state, _ ->
            if (!BlockBreakerConfig.enabled) return@Before true // Allow breaking if disabled

            if (shouldPreventBreak(state)) {
                player.sendMessage(Text.literal("You cannot break this block!"), true)
                return@Before false // Cancel the break
            }

            true // Allow break
        })

        // Register toggle command
        net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(registerBlockBreakerCommand())
        }

        logger.info("BlockBreaker initialized successfully!")
    }

    fun shouldPreventBreak(state: BlockState): Boolean {
        val block = state.block
        val id: String = Registries.BLOCK.getId(block).toString()

        return when {
            id.contains("apricorn_leaves") -> true
            id.contains("apricorn_log") -> true
            id.contains("apricorn") && !isApricornRipe(state) -> true
            id.endsWith("_berry_bush") || id.contains("berry_bush") -> true
            id.contains("healer") || id.endsWith("healing_machine") -> true
            else -> false
        }
    }

    fun isApricornRipe(state: BlockState): Boolean {
        val ageProperty = state.properties.find { it.name == "age" }
        return ageProperty?.let {
            state.get(it as IntProperty) == 4
        } ?: true // If no age property, assume safe
    }
}