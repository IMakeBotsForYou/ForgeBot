package Bungie.API;

import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import ReturnCodes.CommandCodes;

public class Utils {
	public static boolean printJson = false;

	// Prints Json content in a pretty way
	public static void printJSon(JsonObject json) {
		if (printJson) {
			Gson gson = new Gson();
			gson = new GsonBuilder().setPrettyPrinting().create();
			String str = gson.toJson(json);
			System.out.println("\n" + str);
		}
	}

	// Passes the nickname through various rules to check that it's suitable
	public static boolean isValidateNickname(String name) {
		// Refuse empty input
	    
		if (name.length() < 1) {
		        
		        System.out.println("No name found");
			return false;
		}

		// Battlenets need #
		if (!name.contains("#")) {
			System.out.println("Doesn't contain a '#'");
			return false;
		}

		// Needs correct regional information
		if (countCharOccurances(name, "[") + countCharOccurances(name, "]") != 2) {
			System.out.println("Square Brackets are not as expected");
			return false;
		}
		
		// Check region codes
		String region = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
		if (countCharOccurances(name, "//") > 1) {
			System.out.println("Too many slashes");
			return false;
		} else {
			region = region.replaceFirst("/", "");
		}
		if (region.length() > 6) {
			System.out.println("Too many regions");
			
			return false;
		}
		if (region.replaceFirst("EU", "").replaceFirst("NA", "").replaceFirst("AS", "").length() > 0) {
		        
			System.out.println("Weird characters found in region area");
			return false;
		}

		// All above have passed
		return true;
	}

	public static int countCharOccurances(String text, String c) {
		return text.length() - text.replaceAll(Pattern.quote(c), "").length();
	}

	public static int getDesignatedRaiderRole(RaidStats rs) {

		int totalNormal = 0;
		int totalPrestige = 0;

		totalNormal += rs.getLev().getNormalCompletions();
		totalNormal += rs.getEow().getNormalCompletions();
		totalNormal += rs.getSos().getNormalCompletions();
		totalNormal += rs.getLw().getNormalCompletions();
		totalNormal += rs.getSp().getNormalCompletions();

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
}
