package dev.twelveoclock.cssobot.modules.sub

import dev.twelveoclock.cssobot.modules.UserModule
import dev.twelveoclock.cssobot.modules.base.ListenerModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PointModule(val userModule: UserModule, override val jda: JDA) : ListenerModule() {

	override fun onEnable() {
		// Join all users
	}

	override fun onDisable() {
		// Make all users leave
	}


	override fun onMessageReceived(event: MessageReceivedEvent) {

	}

}