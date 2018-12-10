package Bungie.API;

public class Activity {
	String[] normalHashes;
	String[] prestigeHashes;
	int normalCompletions;
	int prestigeCompletions;

	public Activity(String[] normalHashes, String[] prestigeHashes, int normalCompletions, int prestigeCompletion) {
		super();
		this.normalHashes = normalHashes;
		this.prestigeHashes = prestigeHashes;
		this.normalCompletions = normalCompletions;
		this.prestigeCompletions = prestigeCompletion;
		
	}

	public Activity(String[] normalHashes, String[] prestigeHashes) {
		this(normalHashes, prestigeHashes, 0, 0);
	}

	public Activity() {
		this(null, null, 0, 0);
	}

	public void addNormalCompletions(int completions) {
		normalCompletions += completions;
	}

	public void addPrestigeCompletions(int completions) {
		prestigeCompletions += completions;
	}

	public int getCompletions() {
		return normalCompletions + prestigeCompletions;
	}

	public String[] getNormalHashes() {
		return normalHashes;
	}

	public String[] getPrestigeHashes() {
		return prestigeHashes;
	}

	public int getNormalCompletions() {
		return normalCompletions;
	}

	public int getPrestigeCompletions() {
		return prestigeCompletions;
	}

	public void setNormalHashes(String[] normalHashes) {
		this.normalHashes = normalHashes;
	}

	public void setPrestigeHashes(String[] prestigeHashes) {
		this.prestigeHashes = prestigeHashes;
	}
	
	public void setNormalCompletions(int normalCompletions) {
		this.normalCompletions = normalCompletions;
	}

	public void setPrestigeCompletions(int prestigeCompletions) {
		this.prestigeCompletions = prestigeCompletions;
	}

}
