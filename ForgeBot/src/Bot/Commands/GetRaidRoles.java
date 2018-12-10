package Bot.Commands;

import java.util.regex.Pattern;

import Bungie.API.RaidStats;
import Bungie.API.Request;
import Bungie.API.Utils;
import Discord.Output.Embed;
import ReturnCodes.CommandCodes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class GetRaidRoles {
	public static void run(IMessage message) {
		String nickname = message.getAuthor().getDisplayName(message.getGuild());
		//String bnet = getBattleNetFromNickname(message.getAuthor().getDisplayName(message.getGuild()));
		IUser user = message.getAuthor();
		IGuild guild = message.getGuild();
		IChannel channel = message.getChannel();
		IRole prestigeRaider = null;
		IRole normalRaider = null;
		IRole newRaider = null;
		IRole guardian = null;
		IRole questionalble = null;

		try {
			prestigeRaider = guild.getRolesByName("Prestige Raider").get(0);
			normalRaider = guild.getRolesByName("Normal Raider").get(0);
			newRaider = guild.getRolesByName("New Raider").get(0);
			guardian = guild.getRolesByName("Guardian").get(0);
			questionalble = guild.getRolesByName("Questionable").get(0);
		} catch (Exception e) {
			System.out.println("Error retrieving server raider roles");
			return;
		}

		if (Utils.isValidateNickname(nickname)) {
			message.getChannel().setTypingStatus(true);
			RaidStats rs = Request.collectAllRaidStats(getBattleNetFromNickname(message.getAuthor().getDisplayName(message.getGuild())));
			Embed.raidStats(message, rs);

			switch (getDesignatedRaiderRole(rs)) {
			
			case CommandCodes.PrestigeRaider:
				if (user.hasRole(prestigeRaider)) {
					//DiscordCommands.sendMessage(channel, String.format("User [%s] already has [%s]", bnet, prestigeRaider.getName()));
				} else {
					user.addRole(prestigeRaider);			
					//DiscordCommands.sendMessage(channel, String.format("User [%s] has been given [%s]", bnet, prestigeRaider.getName()));
				}
				if (user.hasRole(normalRaider)) {
					user.removeRole(normalRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", normalRaider.getName(), bnet));
				}
				if (user.hasRole(newRaider)) {
					user.removeRole(newRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", newRaider.getName(), bnet));
				}
				break;
			case CommandCodes.NormalRaider:
				if (user.hasRole(normalRaider)) {
					//DiscordCommands.sendMessage(channel, String.format("User [%s] already has [%s]", bnet, normalRaider.getName()));
				} else {
					user.addRole(normalRaider);
					//DiscordCommands.sendMessage(channel, String.format("User [%s] has been given [%s]", bnet, normalRaider.getName()));
				}
				if (user.hasRole(prestigeRaider)) {
					user.removeRole(prestigeRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", prestigeRaider.getName(), bnet));
				}
				if (user.hasRole(newRaider)) {
					user.removeRole(newRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", newRaider.getName(), bnet));
				}
				break;
			case CommandCodes.NewRaider:
				if (user.hasRole(newRaider)) {
					//DiscordCommands.sendMessage(channel, String.format("User [%s] already has [%s]", bnet, newRaider.getName()));
				} else {
					user.addRole(newRaider);
					//DiscordCommands.sendMessage(channel, String.format("User [%s] has been given [%s]", bnet, newRaider.getName()));
				}
				if (user.hasRole(prestigeRaider)) {
					user.removeRole(prestigeRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", prestigeRaider.getName(), bnet));
				}
				if (user.hasRole(normalRaider)) {
					user.removeRole(normalRaider);
					//DiscordCommands.sendMessage(channel, String.format("Role [%s] has been removed from [%s]", normalRaider.getName(), bnet));
				}
				break;
			}
				
			switch (getServerRoles()) {
                        case CommandCodes.Guardian:
                                if (user.hasRole(guardian)) {                                     
                                
                                } else {
                                    user.addRole(guardian);
                                    
                                if (user.hasRole(questionalble)) {
                                        user.removeRole(questionalble);
                                }
                                break;

                        }
		}
                       

			message.getChannel().setTypingStatus(false);
		} else {
		    Utils error = null;
                    Embed.error(message, error);
		}
		
	}

	public static int getDesignatedRaiderRole(RaidStats rs) {

		int totalNormal = 0;
		int totalPrestige = 0;

		totalNormal += rs.getLev().getNormalCompletions();
		totalNormal += rs.getEow().getNormalCompletions();
		totalNormal += rs.getSos().getNormalCompletions();
		totalNormal += rs.getLw().getNormalCompletions();

		totalPrestige += rs.getLev().getPrestigeCompletions();
		totalPrestige += rs.getEow().getPrestigeCompletions();
		totalPrestige += rs.getSos().getPrestigeCompletions();

		if (totalPrestige >= 5) {
			return CommandCodes.PrestigeRaider;
		} else if (totalNormal + totalPrestige >= 5) {
			return CommandCodes.NormalRaider;
		} else {
			return CommandCodes.NewRaider;
		}
		    
		
	}
	
	public static int getServerRoles() {
	    return CommandCodes.Guardian;
	}

	public static RaidStats getRaidStats(IMessage message) {
		return Request.collectAllRaidStats(getBattleNetFromNickname(message.getAuthor().getDisplayName(message.getGuild())));
	}

	public static void isValidateNickname(IMessage message) {
	    
	}
	
	// Returns the BattleNet from a given (Validated) Nickname
	private static String getBattleNetFromNickname(String name) {
		return name.replaceFirst(Pattern.quote(name.substring(name.indexOf("["), name.indexOf("]") + 1)), "").replaceAll(" ", "");
	}
}
