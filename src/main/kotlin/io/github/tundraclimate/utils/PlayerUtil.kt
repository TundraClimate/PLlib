package io.github.tundraclimate.utils

import io.github.tundraclimate.utils.container.PlayerContainer
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Content
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

private val utils: MutableMap<UUID, PlayerUtil> = mutableMapOf()

fun Player.getUtil(): PlayerUtil? {
    return PlayerUtil.getPlayerUtil(this)
}

fun Player.getUtilOrThrow(): PlayerUtil {
    return PlayerUtil.getPlayerUtilOrThrow(this)
}

fun <T> PlayerContainer?.isNull(isnull: () -> T) {
    if (this == null) isnull()
}

fun <T> PlayerContainer?.isNotNull(isNotNull: () -> T) {
    if (this != null) isNotNull()
}

fun Player.sendHoverMessage(msg: String, _text: String) {
    val hover = TextComponent().apply {
        text = _text
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Collections.singletonList(Text(msg) as Content))
    }
    this.spigot().sendMessage(hover)
}

fun Player.sendClickMessage(msg: String, _text: String, action: ClickEvent.Action = ClickEvent.Action.RUN_COMMAND) {
    val click = TextComponent().apply {
        text = _text
        clickEvent = ClickEvent(action, msg)
    }
    this.spigot().sendMessage(click)
}

fun Player.sendClickableHoverMessage(
    hover: String,
    click: String,
    _text: String,
    action: ClickEvent.Action = ClickEvent.Action.RUN_COMMAND
) {
    val component = TextComponent().apply {
        text = _text
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Collections.singletonList(Text(hover) as Content))
        clickEvent = ClickEvent(action, click)
    }
    this.spigot().sendMessage(component)
}

fun Player.sendClickableHoverMessage(hoverEvent: HoverEvent, clickEvent: ClickEvent) {
    val textComponent = TextComponent().also {
        it.hoverEvent = hoverEvent
        it.clickEvent = clickEvent
    }
    this.spigot().sendMessage(textComponent)
}

fun JavaPlugin.runTask(async: Boolean = false, task: () -> Unit) {
    if (!async) Bukkit.getScheduler().runTask(this, task)
    else Bukkit.getScheduler().runTaskAsynchronously(this, task)
}

fun Plugin.runTask(async: Boolean = false, task: () -> Unit) {
    if (!async) Bukkit.getScheduler().runTask(this, task)
    else Bukkit.getScheduler().runTaskAsynchronously(this, task)
}

fun Player.runTask(plugin: JavaPlugin, async: Boolean = false, task: (Player) -> Unit) {
    val pl = this
    val run = object : BukkitRunnable() {
        override fun run() {
            task(pl)
        }
    }
    if (!async) run.runTask(plugin)
    else run.runTaskAsynchronously(plugin)
}

class PlayerUtil private constructor(private val player: Player) {
    private val containers: MutableMap<Any?, PlayerContainer?> = mutableMapOf()

    val trimUUID = player.uniqueId.toString().replace("-", "")

    var health: Double = 20.0
        get() = player.health
        set(value) {
            field = value
            player.health = value
        }
    var maxHealth: Double = 20.0
        get() {
            return player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
        }
    var attackDamage: Double = 1.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)?.value ?: 1.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)?.baseValue = value
        }
    var speed: Double = 0.10000000149011612
        get() {
            return player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.value ?: 0.10000000149011612
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = value
        }
    var attackSpeed: Double = 4.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.value ?: 4.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = value
        }
    var armor: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_ARMOR)?.value ?: 0.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_ARMOR)?.baseValue = value
        }
    var knockBack: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)?.value ?: 0.0
        }
        set(value) {
            field = value
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)?.baseValue = value
        }
    var luck: Double = 0.0
        get() {
            return player.getAttribute(Attribute.GENERIC_LUCK)?.value ?: 0.0
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

        @JvmStatic
        fun getPlayerUtilOrThrow(uuid: UUID): PlayerUtil {
            return if (utils.containsKey(uuid)) utils[uuid] ?: throw IllegalArgumentException("$uuid is Not Found")
            else {
                val player = Bukkit.getPlayer(uuid) ?: throw IllegalArgumentException("Player for $uuid is Not Found")
                val util = PlayerUtil(player)
                utils[uuid] = util
                util
            }
        }

        @JvmStatic
        fun getPlayerUtilOrThrow(uuid: String): PlayerUtil {
            return getPlayerUtilOrThrow(UUID.fromString(uuid))
        }

        @JvmStatic
        fun getPlayerUtilOrThrow(player: Player): PlayerUtil {
            return getPlayerUtilOrThrow(player.uniqueId)
        }
    }

    fun addContainer(container: Any) {
        containers[container] = PlayerContainer(player)
    }

    fun setContainer(container: Any, ct: PlayerContainer) {
        containers[container] = ct
    }

    fun getContainer(container: Any): PlayerContainer {
        return containers[container] ?: throw NoSuchElementException("Player does not hold \"$container\"")
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

    fun resetAttributes() {
        maxHealth = 20.0
        attackDamage = 1.0
        speed = 0.10000000149011612
        attackSpeed = 4.0
        armor = 0.0
        knockBack = 0.0
        luck = 0.0
    }

    fun addPotionEffect(
        type: PotionEffectType,
        duration: Int = 30,
        amplifier: Int = 0,
        ambient: Boolean = true,
        particles: Boolean = true,
        icon: Boolean = true
    ) {
        player.addPotionEffect(PotionEffect(type, duration, amplifier, ambient, particles, icon))
    }

    fun getPotionEffects(): List<PotionEffect> = player.activePotionEffects.toList()
}