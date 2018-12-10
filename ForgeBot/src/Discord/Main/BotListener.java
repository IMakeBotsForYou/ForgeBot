package Discord.Main;

import Discord.API.CommandProcessor;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class BotListener {
	public static String prefix = new String("!");

	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event) throws Exception {
		String content = event.getMessage().getContent();
		if (content.toLowerCase().startsWith(prefix)) {
			if (content.length() > 1) {
				if (Character.isAlphabetic(content.charAt(1))) {
					CommandProcessor.processCommand(event.getMessage(), prefix);
				}
			}
		}
	}
}
