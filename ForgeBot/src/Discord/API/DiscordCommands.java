package Discord.API;

import java.util.ArrayList;
import java.util.List;

import ReturnCodes.APICodes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.EmbedBuilder;

public class DiscordCommands {

	public static void unknownError(IChannel channel) {
		sendMessage(channel, "Unknown error occured, please contact bot support.");
	}

	public static void sendMessage(IChannel channel, String msg) {
		System.out.println("Sending message [" + msg + "] to [#" + channel.getName() + "]");
		channel.sendMessage(msg);
	}

	public static void sendMessage(IChannel channel, EmbedBuilder msg) {
		System.out.println("Sending message [" + msg + "] to [#" + channel.getName() + "]");
		channel.sendMessage(msg.build());
	}

	public static void deleteMessage(IMessage message) {
		System.out.println("Deleting message [" + message.getContent() + "] from [" + message.getChannel());
		message.delete();
	}

	public static int assignRole(IMessage message, IGuild guild, String roleName) {
		List<IRole> serverRoles = new ArrayList<IRole>();
		String userNickname = message.getAuthor().getDisplayName(guild);
		boolean foundServer = false;
		boolean foundUser = false;
		serverRoles = guild.getRoles();
		System.out.println(String.format("Attempting to assign role [%s] to [%s]", roleName, userNickname));
		for (IRole serverRole : serverRoles) {
			if (serverRole.getName().equals(roleName)) {
				foundServer = true;
				for (IRole userRole : message.getAuthor().getRolesForGuild(guild)) {
					if (userRole.equals(serverRole)) {
						foundUser = true;
					}
				}
				if (!foundUser) {
					message.getAuthor().addRole(serverRole);
				}
			}
		}
		if (foundServer && !foundUser) {
			System.out.println(String.format("Successfully assigned role [%s] to [%s]", roleName, userNickname));
			return APICodes.Success;
		} else if (foundServer && foundUser) {
			System.out.println(String.format("User [%s] already has the role [%s]", userNickname, roleName));
			return APICodes.UserAlreadyHasRole;
		} else {
			System.out.println(String.format("Role [%s] could not be found on the server", roleName));
			return APICodes.RoleNotFoundServer;
		}
	}

	public static int removeRole(IMessage message, IGuild guild, String roleName) {
		List<IRole> userRoles = new ArrayList<IRole>();
		boolean found = false;
		userRoles = message.getAuthor().getRolesForGuild(guild);
		System.out.println(String.format("Attempting to remove role [%s] from [%s]", roleName, message.getAuthor().getDisplayName(guild)));
		for (IRole role : userRoles) {
			if (role.getName().equals(roleName)) {
				message.getAuthor().removeRole(role);
				found = true;
			}
		}
		if (found) {
			System.out.println(String.format("Successfully removed role [%s] from [%s]", roleName, message.getAuthor().getDisplayName(guild)));
			return APICodes.Success;
		} else {
			System.out.println(String.format("Role [%s] could not be found for the user [%s]", roleName, message.getAuthor().getDisplayName(guild)));
			return APICodes.UserDoesntHaveRole;
		}
	}
}
