package dev.twelveoclock.cssobot

import dev.twelveoclock.lang.crescent.lexers.CrescentLexer
import dev.twelveoclock.lang.crescent.parsers.CrescentParser
import dev.twelveoclock.lang.crescent.vm.CrescentVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.Duration.Companion.seconds

object CSSOBot : ListenerAdapter() {

    @JvmStatic
    fun main(args: Array<String>) {

        val jda = JDABuilder.createLight(Path("token.txt").readText(), emptyList())
            .addEventListeners(this)
            .setActivity(Activity.playing("Stuck in the matrix"))
            .build()

        jda.upsertCommand("crescent", "Run crescent code")
            .addOption(OptionType.STRING, "code", "The code to execute", true)
            .queue()

        println("Started! ${jda.getInviteUrl()}")
    }


    override fun onSlashCommand(event: SlashCommandEvent) {

        if (event.name != "crescent") {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {

            val job = launch(Dispatchers.IO) {
                try {

                    val code = event.getOption("code")!!.asString.replace("`", "").trim()

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

            if (job.isActive) {
                job.cancel()
                event.channel.sendMessage("Code took longer than 10 seconds to run").queue()
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