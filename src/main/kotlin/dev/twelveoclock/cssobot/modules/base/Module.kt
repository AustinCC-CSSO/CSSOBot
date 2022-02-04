package dev.twelveoclock.cssobot.modules.base

/**
 * The base for something that can be enabled or disabled
 */
interface Module {

    /**
     * Whether the module is enabled
     */
    val isEnabled: Boolean


    /**
     * Enables the module
     */
    fun enable()

    /**
     * Disabled the module
     */
    fun disable()

}