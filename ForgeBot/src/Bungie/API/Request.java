package Bungie.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Request {
	static String apiKey = "e4582b11309b41df8667ba6523a0f9ff";
	static String domainBase = "https://www.bungie.net";

	public static RaidStats collectAllRaidStats(String name) {
		String memberID;
		String[] characterIDs;
		RaidStats rs = new RaidStats();

		try {
			domainBase = "https://www.bungie.net";
			apiKey = "e4582b11309b41df8667ba6523a0f9ff";
			memberID = getMemberID(name);
			characterIDs = getCharacterIDs(memberID);
			for (int i = 0; i < characterIDs.length; i++) {
				rs.combineRaidStats(collectCharacterActivityCompletions(memberID, characterIDs[i]));
			}
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		return rs;
	}

	private static RaidStats collectCharacterActivityCompletions(String memberID, String characterID) throws IOException {
		// Init
		String url = domainBase + "/Platform/Destiny2/4/Account/" + memberID + "/Character/" + characterID + "/Stats/AggregateActivityStats/";
		JsonObject json;
		JsonArray jray;
		Activity lev = new Activity();
		Activity eow = new Activity();
		Activity sos = new Activity();
		Activity lw = new Activity();
		Activity sp = new Activity();
		RaidStats r = new RaidStats();

		// Collect
		json = get(url);
		jray = json.getAsJsonObject("Response").getAsJsonArray("activities");

		// Print
		Utils.printJSon(json);

		// Leviathan - Setup and Colect
		lev.setNormalHashes(new String[] { "2693136601", "2693136600", "2693136602", "2693136604", "2693136603", "2693136605" });
		lev.setPrestigeHashes(new String[] { "1685065161", "3446541099", "757116822", "3879860661", "2449714930", "417231112" });
		lev.setNormalCompletions(searchJsonActivities(jray, lev.getNormalHashes()));
		lev.setPrestigeCompletions(searchJsonActivities(jray, lev.getPrestigeHashes()));
		r.setLev(lev);

		// Eater of Worlds - Setup and Collect
		eow.setNormalHashes(new String[] { "3089205900", "2164432138", "809170886" });
		eow.setPrestigeHashes(new String[] { "809170886" });
		eow.setNormalCompletions(searchJsonActivities(jray, eow.getNormalHashes()));
		eow.setPrestigeCompletions(searchJsonActivities(jray, eow.getPrestigeHashes()));
		r.setEow(eow);

		// Spire of Stars - Setup and Collect
		sos.setNormalHashes(new String[] { "119944200", "3004605630" });
		sos.setPrestigeHashes(new String[] { "3213556450" });
		sos.setNormalCompletions(searchJsonActivities(jray, sos.getNormalHashes()));
		sos.setPrestigeCompletions(searchJsonActivities(jray, sos.getPrestigeHashes()));
		r.setSos(sos);
		
		// Last Wish - Setup and Collect
		lw.setNormalHashes(new String[] { "2214608157", "2122313384", "1661734046", "2214608156" });
		//lw.setPrestigeHashes(new String[] { "2214608156" });
                lw.setNormalCompletions(searchJsonActivities(jray, lw.getNormalHashes()));
                //lw.setPrestigeCompletions(searchJsonActivities(jray, lw.getPrestigeHashes()));
		r.setLw(lw);
		
		// Scourge of the Past - Setup and Collect
		sp.setNormalHashes(new String[] {"548750096", "2812525063" });
		sp.setNormalCompletions(searchJsonActivities(jray, sp.getNormalHashes()));
		r.setSp(sp);
		return r;
	}

	private static int searchJsonActivities(JsonArray jray, String[] hashes) {
		// Init
		String hash;
		int count = 0;
		int completions = 0;
		JsonObject tmpJson;

		// Loop, Lookup, and Commulate
		for (int i = 0; i < jray.size(); i++) {
			tmpJson = jray.get(i).getAsJsonObject();
			hash = tmpJson.get("activityHash").getAsString();
			for (int j = 0; j < hashes.length; j++) {
				if (hash.equals(hashes[j])) {
					completions = tmpJson.getAsJsonObject("values").getAsJsonObject("activityCompletions").getAsJsonObject("basic").get("value").getAsInt();
					count += completions;
				}
			}
		}
		return count;
	}

	private static String getMemberID(String name) throws Exception {
		// Init
		name = name.replace("#", "%23");
		String url = domainBase + "/Platform/Destiny2/SearchDestinyPlayer/-1/" + name + "/";
		JsonObject json = get(url);

		return json.getAsJsonArray("Response").get(0).getAsJsonObject().get("membershipId").getAsString();
	}

	private static String[] getCharacterIDs(String memberID) throws IOException {
		// Init
	        ///Platform/Destiny2/4/Account/4611686018471593174/Stats/
		String url = domainBase + "/Platform/Destiny2/4/profile/" + memberID + "/?components=Profiles%2CCharacters";
		JsonObject json = get(url);
		JsonArray jray;
		String[] out;

		json = json.getAsJsonObject("Response");
		json = json.getAsJsonObject("profile");
		json = json.getAsJsonObject("data");
		jray = json.getAsJsonArray("characterIds");

		// Conver JsonArray to String output
		out = new String[jray.size()];
		for (int i = 0; i < jray.size(); i++) {
			out[i] = jray.get(i).getAsString();
			System.out.println("Found Character ID: " + out[i]);
		}
		return out;
	}

	private static JsonObject get(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Set Request
		con.setRequestMethod("GET");

		// Set header
		con.setRequestProperty("X-API-KEY", apiKey);

		// Log Response
		System.out.println("Sending 'GET' request to Bungie.Net : " + url);
		System.out.println("Response Code : " + con.getResponseCode() + " [" + con.getResponseMessage() + "]");

		// Retrieve
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		String response = "";
		while ((inputLine = in.readLine()) != null) {
			response += inputLine;
		}

		// Close
		in.close();

		// Parse
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(response);

		return json;
	}
}