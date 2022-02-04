package dev.twelveoclock.cssobot.modules.base

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.hooks.ListenerAdapter

abstract class ListenerModule : ListenerAdapter(), Module {

    abstract val jda: JDA

    final override var isEnabled = false
        private set


    protected open fun onEnable() {}

    protected open fun onDisable() {}


    final override fun enable() {
        if (!isEnabled) {
            onEnable()
            jda.addEventListener(this)
            isEnabled = true
        }
    }

    final override fun disable() {
        if (isEnabled) {
            onDisable()
            jda.removeEventListener(this)
            isEnabled = false
        }
    }

}