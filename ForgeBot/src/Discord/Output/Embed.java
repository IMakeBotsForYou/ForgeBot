package Discord.Output;

import Bungie.API.Activity;
import Bungie.API.RaidStats;
import Bungie.API.Utils;
import Discord.API.DiscordCommands;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

public class Embed {

	public static void raidStats(IMessage message, RaidStats rs) {
		Activity lev = rs.getLev();
		Activity eow = rs.getEow();
		Activity sos = rs.getSos();
		Activity lw = rs.getLw();
		Activity sp = rs.getSp();
		EmbedBuilder eb = new EmbedBuilder();
		    eb.withAuthorName(message.getAuthor().getDisplayName(message.getGuild()));
		    eb.withAuthorIcon(message.getAuthor().getAvatarURL());
		    eb.withDescription(String.format("Has completed **%s** total raids", rs.getTotalCompletion()));
		    eb.withColor(0, 255, 0);

		// Fields
		    eb.appendField(String.format("Leviathan: %d", lev.getCompletions()), String.format("**N:** %d\t** P:** %d", lev.getNormalCompletions(), lev.getPrestigeCompletions()), true);
		    eb.appendField(String.format("Eater of Worlds: %d", eow.getCompletions()), String.format("**N:** %d\t** P:** %d", eow.getNormalCompletions(), eow.getPrestigeCompletions()), true);
		    eb.appendField(String.format("Spire of Stars: %d", sos.getCompletions()), String.format("**N:** %d\t** P:** %d", sos.getNormalCompletions(), sos.getPrestigeCompletions()), true);
		    eb.appendField(String.format("Last Wish: %d", lw.getCompletions()), String.format("** N:** %d", lw.getNormalCompletions()), false);
		    eb.appendField(String.format("Scourge of the Past: %d", sp.getCompletions()), String.format("**N:** %d", sp.getNormalCompletions()),true);

		DiscordCommands.sendMessage(message.getChannel(), eb);

	}


	public static void error(IMessage message, Utils error) {
	     EmbedBuilder err = new EmbedBuilder();
	    
	         err.appendField("Nickname does not match correct format", "Please edit your nickname\n\n", false);
	         err.appendField("----Correct Format----" , "`[region] battlenetID#1234`\nExample:\n[NA] ROMVoid#1909", true);
	         err.appendField("Acceptable Regions", "**NA:** North America\n**EU:** Europe\n** AS:** Asia", true);
	         err.withAuthorName(message.getAuthor().getDisplayName(message.getGuild()));
	         err.withColor(255, 0, 0);
	         err.withTitle(" :no_entry:  ERROR  :no_entry: ");
	         err.appendField("Please visit channel below for more info", "<#519325355451088928>", false);

        DiscordCommands.sendMessage(message.getChannel(), err);

	}
}

