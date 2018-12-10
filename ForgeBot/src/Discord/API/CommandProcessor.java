package Discord.API;

import Bot.Commands.GetRaidRoles;
import Bot.Commands.GetRaidStats;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

public class CommandProcessor {

	public static void processCommand(IMessage message, String prefix) throws Exception {

		IGuild guild = message.getGuild();

		String[] command = message.getContent().replaceFirst(prefix, "").split(" ");

		System.out.println("Command received: [" + message.toString().replaceFirst(prefix, "") + "] from [" + message.getAuthor().getDisplayName(guild) + "]");

		switch (command[0].toLowerCase()) {

		case "raids":
			System.out.println("Activating raids command");
			GetRaidStats.run(message);
			break;
		case "getroles":
			System.out.println("Activating getRoles command");
			GetRaidRoles.run(message);
			break;
		default:
			System.out.println(String.format("[%s] isn't a valid command", command[0]));
		}
	}
}
