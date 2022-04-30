package io.github.tundraclimate.utils.container.factory

import io.github.tundraclimate.utils.container.ContainerFactory
import io.github.tundraclimate.utils.container.PlayerContainer
import io.github.tundraclimate.utils.getUtilOrThrow
import org.bukkit.entity.Player

object InfoPlayerContainer : ContainerFactory {
    override fun create(player: Player): PlayerContainer {
        val container = PlayerContainer(player)
        val util = player.getUtilOrThrow()
        val uuid = player.uniqueId.toString()
        val uuids = mapOf("default" to uuid, "trim" to uuid.replace("-", ""))
        val status: Map<String, Double> = mapOf(
            "health" to player.health,
            "max_health" to util.maxHealth,
            "attackDamage" to util.attackDamage,
            "speed" to util.speed,
            "knockBack" to util.knockBack,
            "armor" to util.armor,
            "attackSpeed" to util.attackSpeed,
            "luck" to util.luck
        )
        container.setData("uuid", uuids)
        container.setData("player", container.owner)
        container.setData("location", player.location)
        container.setData("status", status)
        container.setData("effect", util.getPotionEffects())
        return container
    }
}