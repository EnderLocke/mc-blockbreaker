package com.ender.blockbreaker

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.BlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.registry.Registries
import net.minecraft.state.property.IntProperty
import net.minecraft.text.Text

object BlockBreakerMod : ModInitializer {
    override fun onInitialize() {
        println("BlockBreaker initializing...")

        BlockBreakEvents.BEFORE.register(BlockBreakEvents.Before { world, pos, state, player ->
            if (!world.isClient && shouldPreventBreak(state)) {
                player.sendMessage(Text.literal("You can't break this block!"), true)
                return@Before false
            }
            true
        })

        println("BlockBreaker initialized successfully.")
    }

    private fun shouldPreventBreak(state: BlockState): Boolean {
        val id = Registries.BLOCK.getId(state.block).toString()

        return when {
            id.contains("apricorn_leaves") -> true
            id.contains("apricorn_log") -> true
            id.contains("apricorn") && !isApricornRipe(state) -> true
            else -> false
        }
    }

    private fun isApricornRipe(state: BlockState): Boolean {
        val ageProperty = state.properties.find { it.name == "age" } as? IntProperty
        return ageProperty?.let { state.get(it) == 4 } ?: true
    }
}