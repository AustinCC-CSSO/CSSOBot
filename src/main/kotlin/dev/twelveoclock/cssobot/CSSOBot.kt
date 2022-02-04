package dev.twelveoclock.cssobot

import dev.twelveoclock.cssobot.modules.CommandModule
import dev.twelveoclock.cssobot.modules.UserModule
import dev.twelveoclock.cssobot.modules.base.Module
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import kotlin.concurrent.thread
import kotlin.io.path.Path
import kotlin.io.path.readText

object CSSOBot : ListenerAdapter() {

    @JvmStatic
    fun main(args: Array<String>) {

        /* Start JDA */
        val jda = JDABuilder.createLight(Path("token.txt").readText(), listOf(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES))
            .enableCache(CacheFlag.VOICE_STATE)
            .addEventListeners(this)
            .setActivity(Activity.playing("Stuck in the matrix"))
            .build()

        jda.awaitReady()

        /* Start modules */
        val userModule = UserModule(jda).apply(Module::enable)
        val commandModule = CommandModule(jda).apply(Module::enable)

        println("Started! ${jda.getInviteUrl()}")

        /* Add shutdown hook */
        Runtime.getRuntime().addShutdownHook(thread {
            userModule.disable()
            commandModule.disable()
        })
    }

}