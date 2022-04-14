package com.tundra.pllib.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.entity.Player

class PlayerContainer(val owner: Player) {

    private var container: MutableMap<Any, Any?> = mutableMapOf()

    fun setData(key: Any, data: Any?) {
        container[key] = data
    }

    fun removeData(key: Any) {
        container.remove(key)
    }

    fun getData(key: Any): Any? {
        return container[key]
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
}
