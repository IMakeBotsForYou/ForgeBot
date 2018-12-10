package Discord.Main;

public class BotStats {
	private static long startTime;

	public static void startUpTime() {
		startTime = System.currentTimeMillis();
	}

	public static long getUptime() {
		return startTime;
	}
}
