package com.tundra.pllib

import com.tundra.pllib.utils.getUtil
import org.bukkit.Bukkit

class PLlib {
    fun get(){
        val util = Bukkit.getPlayer("")?.getUtil() ?: return
    }
}