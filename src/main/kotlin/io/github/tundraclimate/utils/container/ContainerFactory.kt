package io.github.tundraclimate.utils.container

import org.bukkit.entity.Player

interface ContainerFactory {
    fun create(player: Player): PlayerContainer
}