package Discord.Main;

import java.io.IOException;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class BotMain {

	public static String apiKey;
	public static IDiscordClient bot;

	public static void main(String args[]) {
		try {
			startBot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static IDiscordClient createClient(String token, boolean login) {
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(token);
		try {
			if (login) {
				return clientBuilder.login();
			} else {
				return clientBuilder.build();
			}
		} catch (DiscordException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static void startBot() throws IOException {

		bot = createClient(" PLACE TOKEN HERE ", true);
		EventDispatcher dis = bot.getDispatcher();
		dis.registerListener(new BotListener());

		BotStats.startUpTime();
	}
}
