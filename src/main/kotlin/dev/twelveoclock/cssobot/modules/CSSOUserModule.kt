package dev.twelveoclock.cssobot.modules

import dev.twelveoclock.cssobot.data.CSSOUser
import dev.twelveoclock.cssobot.modules.base.ListenerModule
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import java.nio.file.Path
import kotlin.io.path.*

class CSSOUserModule(val guild: Guild, override val jda: JDA) : ListenerModule() {

    // ID -> Join Time
    val userJoinTime = mutableMapOf<Long, Long>()

    val guildFolder = Path("${guild.name}:${guild.id}")

    val usersFolder = guildFolder.resolve("Users")


    override fun onEnable() {

        if (guildFolder.notExists()) {
            guildFolder.createDirectories()
        }

        guild.voiceChannels
            .flatMap(VoiceChannel::getMembers)
            .forEach { voiceJoin(it.idLong) }
    }

    override fun onDisable() {
        userJoinTime.keys.forEach(::voiceLeave)
    }


    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
        voiceJoin(event.entity.idLong)
    }

    override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent) {
        voiceLeave(event.entity.idLong)
    }


    private fun voiceJoin(userID: Long) {
        userJoinTime[System.currentTimeMillis()] = userID
    }

    private fun voiceLeave(userID: Long) {

        val joinTime = userJoinTime.remove(System.currentTimeMillis()) ?: return
        System.currentTimeMillis() - joinTime

        val user =

        TODO("Save System.currentTimeMillis() - joinTime")
    }

    private fun userPathFor(userID: Long): Path {
        return usersFolder.resolve("$userID.json")
    }

    private fun readOrCreateUser(userID: Long): CSSOUser {

        val userPath = userPathFor(userID)

        if (userPath.exists()) {
            return json.decodeFromString(CSSOUser.serializer(), userPath.readText())
        }

        return CSSOUser(userID).also {
            userPath.writeText(json.encodeToString(CSSOUser.serializer(), it))
        }
    }


    companion object {

        val json = Json { prettyPrint = true }

    }

}