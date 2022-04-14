package com.tundra.pllib.utils

import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import java.lang.NullPointerException
import java.util.*

private val utils: MutableMap<UUID, PlayerUtil> = mutableMapOf()

fun Player.getUtil(): PlayerUtil? {
    return PlayerUtil.getPlayerUtil(this)
}

class PlayerUtil private constructor(private val player: Player) {
    private val containers: MutableMap<Any?, PlayerContainer?> = mutableMapOf()

    var health: Double = 20.0
        get() = player.health
        set(value) {
            field = value
            player.health = value
        }
    var maxHealth: Double = 20.0
        get() {
            return player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value?: 20.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
        }
    var attackDamage: Double = 1.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)?.value?: 1.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)?.baseValue = value
        }
    var speed: Double = 0.10000000149011612
        get() {
            return player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.value?: 0.10000000149011612
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = value
        }
    var attackSpeed: Double = 4.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.value?: 4.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = value
        }
    var armor: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ARMOR)?.value?: 0.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ARMOR)?.baseValue = value
        }
    var knockBack: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)?.value?: 0.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)?.baseValue = value
        }
    var luck: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_LUCK)?.value?: 0.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_LUCK)?.baseValue = value
        }

    companion object {
        @JvmStatic
        fun getPlayerUtil(uuid: UUID): PlayerUtil? {
            if (utils.containsKey(uuid)) return utils[uuid]
            else {
                val player = Bukkit.getPlayer(uuid)
                if (player != null) {
                    val util = PlayerUtil(player)
                    utils[uuid] = util
                    return util
                }
            }
            return null
        }

        @JvmStatic
        fun getPlayerUtil(uuid: String): PlayerUtil? {
            return getPlayerUtil(UUID.fromString(uuid))
        }

        @JvmStatic
        fun getPlayerUtil(player: Player): PlayerUtil? {
            return getPlayerUtil(player.uniqueId)
        }
    }

    fun addContainer(container: Any) {
        containers[container] = PlayerContainer(player)
    }

    fun addContainer(container: Any, ct: PlayerContainer){
        containers[container] = ct
    }

    fun getContainer(container: Any): PlayerContainer {
        return containers[container] ?: throw NullPointerException("Player does not hold \"$container\"")
    }

    fun hasContainer(container: Any): Boolean {
        return containers.containsKey(container)
    }

    fun removeContainer(container: Any) {
        containers.remove(container)
    }

    fun getContainers(): MutableCollection<PlayerContainer?> {
        return containers.values
    }
}