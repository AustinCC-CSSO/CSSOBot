package dev.twelveoclock.cssobot.modules

import dev.twelveoclock.cssobot.modules.base.ListenerModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import java.nio.file.Path
import kotlin.io.path.Path

class UserModule(override val jda: JDA) : ListenerModule() {


    // ID -> Join Time
    val userJoinTime = mutableMapOf<Long, Long>()


    override fun onEnable() {
        // Join all users
    }

    override fun onDisable() {
        // Make all users leave
    }


    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
        userJoinTime[event.entity.idLong] = System.currentTimeMillis()
    }

    override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent) {
        val joinTime = userJoinTime.remove(event.entity.idLong) ?: return
        val userFolder =


        System.currentTimeMillis() - joinTime
    }


    private fun userFileFor(userID: Long): Path {
        return usersFolder.resolve("$userID.json")
    }


    companion object {

        val usersFolder = Path("Users")

    }

}