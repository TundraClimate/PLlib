package io.github.tundraclimate.utils.container

import org.bukkit.entity.Player

data class ContainerValue(val owner: Player, val value: Any?) {
    companion object {
        const val FAILED_VALUE_INT: Int = -1
        const val FAILED_VALUE_LONG: Long = -1L
        const val FAILED_VALUE_DOUBLE: Double = -1.0
        const val FAILED_VALUE_SHORT: Short = -1
        const val FAILED_VALUE_FLOAT: Float = -1.0F
        const val FAILED_VALUE_BYTE: Byte = -1
        const val FAILED_VALUE_CHAR: Char = ' '
    }

    fun toInt(): Int = if (value is Int) value else FAILED_VALUE_INT
    fun toLong(): Long = if (value is Long) value else FAILED_VALUE_LONG
    fun toDouble(): Double = if (value is Double) value else FAILED_VALUE_DOUBLE
    fun toShort(): Short = if (value is Short) value else FAILED_VALUE_SHORT
    fun toFloat(): Float = if (value is Float) value else FAILED_VALUE_FLOAT
    fun toByte(): Byte = if (value is Byte) value else FAILED_VALUE_BYTE
    fun toChar(): Char = if (value is Char) value else FAILED_VALUE_CHAR

    inline fun <T, reified R: List<T>> toListOrEmpty(): List<T> = if (value is R) value else emptyList()
    inline fun <T, reified R: Set<T>> toSetOrEmpty(): Set<T> = if (value is R) value else emptySet()
    inline fun <K, V, reified R: Map<K, V>> toMapOrEmpty(): Map<K, V> = if (value is R) value else emptyMap()

    inline fun <reified R> to(): R? = if (value is R) value else null
}
