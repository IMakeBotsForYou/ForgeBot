package Bot.Commands;

import java.util.regex.Pattern;

import Bungie.API.RaidStats;
import Bungie.API.Request;
import Bungie.API.Utils;
import Discord.Output.Embed;
import sx.blah.discord.handle.obj.IMessage;

public class GetRaidStats {

	public static void run(IMessage message) {
		String nickname = message.getAuthor().getDisplayName(message.getGuild());
		if (Utils.isValidateNickname(nickname)) {
			message.getChannel().setTypingStatus(true);
			RaidStats rs = Request.collectAllRaidStats(getBattleNetFromNickname(message.getAuthor().getDisplayName(message.getGuild())));
			Embed.raidStats(message, rs);
			message.getChannel().setTypingStatus(false);
		} else {
                    Utils error = null;
                    Embed.error(message, error);		}
	}

	// Returns the BattleNet from a given (Validated) Nickname
	private static String getBattleNetFromNickname(String name) {
		return name.replaceFirst(Pattern.quote(name.substring(name.indexOf("["), name.indexOf("]") + 1)), "").replaceAll(" ", "");
	}
}
