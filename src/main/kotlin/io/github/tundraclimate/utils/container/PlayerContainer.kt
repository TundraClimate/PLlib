package io.github.tundraclimate.utils.container

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlayerContainer(val owner: Player) {

    private var container: MutableMap<Any, Any?> = mutableMapOf()

    fun setData(key: Any, data: Any?) {
        container[key] = data
    }

    fun removeData(key: Any) {
        container.remove(key)
    }

    fun getData(key: Any): ContainerValue {
        return ContainerValue(owner, container[key])
    }

    fun hasData(key: Any): Boolean {
        return container.containsKey(key)
    }

    fun isEmpty() = container.isEmpty()

    fun toJson(fixed: Boolean = false): String {
        return if (fixed)
            GsonBuilder().setPrettyPrinting().create().toJson(container)
        else Gson().toJson(container)
    }

    fun toMap(): Map<Any, Any?> = container

    fun toMutableMap(): MutableMap<Any, Any?> = container

    fun clearData() {
        container.clear()
    }

    fun forEach(action: (Map.Entry<Any, Any?>) -> Unit) {
        container.forEach(action)
    }

    fun <R> map(action: (Map.Entry<Any, Any?>) -> R): List<R> {
        return container.map(action)
    }

    fun filter(predicate: (Map.Entry<Any, Any?>) -> Boolean): Map<Any, Any?> {
        return container.filter(predicate)
    }
}
