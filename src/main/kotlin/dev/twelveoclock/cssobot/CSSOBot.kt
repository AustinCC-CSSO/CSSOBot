package dev.twelveoclock.cssobot

import dev.twelveoclock.cssobot.math.ShuntingYard
import dev.twelveoclock.lang.crescent.lexers.CrescentLexer
import dev.twelveoclock.lang.crescent.parsers.CrescentParser
import dev.twelveoclock.lang.crescent.vm.CrescentVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.concurrent.thread
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object CSSOBot : ListenerAdapter() {

    @JvmStatic
    fun main(args: Array<String>) {

        val jda = JDABuilder.createLight(Path("token.txt").readText(), listOf(GatewayIntent.GUILD_MESSAGES))
            .addEventListeners(this)
            .setActivity(Activity.playing("Stuck in the matrix"))
            .build()

        jda.awaitReady()

        println("Started! ${jda.getInviteUrl()}")

        /*
        jda.upsertCommand("crescent", "Run crescent code")
            .addOption(OptionType., "code", "The code to execute", true)
            .queue()

        println("Started! ${jda.getInviteUrl()}&scope=applications.commands")
        */

    }


    override fun onMessageReceived(event: MessageReceivedEvent) {

        val label = event.message.contentRaw.split(' ', '\n').first()

        when(label) {

            "!crescent" -> {

                CoroutineScope(Dispatchers.IO).launch {

                    // Needs to be thread so it can actually be cancelled :C
                    val job = thread {
                        try {

                            val code = event.message.contentRaw.substringAfter(label).replace("`", "").trim()

                            val output = collectSystemOut {
                                val file = CrescentParser.invoke(Path(""), CrescentLexer.invoke(code))
                                CrescentVM(listOf(file), file).invoke()
                            }

                            event.channel.sendMessage("```\n$output```").queue()
                        }
                        catch (ex: Throwable) {
                            event.channel.sendMessage("${ex::class}: ${ex.message}, printed error to console!")
                            ex.printStackTrace()
                        }
                    }

                    delay(10.seconds)

                    if (job.isAlive) {
                        job.stop()
                        event.channel.sendMessage("Code took longer than 10 seconds to run").queue()
                    }
                }
            }

            "!math" -> {
                CoroutineScope(Dispatchers.IO).launch {

                    // Needs to be thread so it can actually be cancelled :C
                    val job = thread {
                        try {

                            val input = event.message.contentRaw.substringAfter(label).replace("`", "").trim()
                            val output = ShuntingYard.evaluate(input)

                            event.channel.sendMessage("```\n${ShuntingYard.invoke(input)}```").queue()
                            event.channel.sendMessage("```\n$output```").queue()
                        }
                        catch (ex: Throwable) {
                            event.channel.sendMessage("${ex::class}: ${ex.message}, printed error to console!").queue()
                            ex.printStackTrace()
                        }
                    }

                    delay(10.seconds)

                    if (job.isAlive) {
                        job.stop()
                        event.channel.sendMessage("Code took longer than 10 seconds to run").queue()
                    }
                }
            }

        }
    }


    private val originalSystemOut = System.out

    private inline fun collectSystemOut(block: () -> Unit): String {

        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream)

        System.setOut(printStream)
        block()
        System.setOut(originalSystemOut)

        return byteArrayOutputStream.toString()
    }

}