package Bungie.API;

public class RaidStats {
	Activity eow;
	Activity sos;
	Activity lev;
	Activity lw;
	Activity sp;

	public RaidStats() {
		lev = new Activity();
		eow = new Activity();
		sos = new Activity();
		lw = new Activity();
		sp = new Activity();
	}

	public void combineRaidStats(RaidStats rs) {
		lev.addNormalCompletions(rs.getLev().getNormalCompletions());
		lev.addPrestigeCompletions(rs.getLev().getPrestigeCompletions());
		eow.addNormalCompletions(rs.getEow().getNormalCompletions());
		eow.addPrestigeCompletions(rs.getEow().getPrestigeCompletions());
		sos.addNormalCompletions(rs.getSos().getNormalCompletions());
		sos.addPrestigeCompletions(rs.getSos().getPrestigeCompletions());
	        lw.addNormalCompletions(rs.getLw().getNormalCompletions());
	        //lw.addPrestigeCompletions(rs.getLw().getPrestigeCompletions());
		sp.addNormalCompletions(rs.getSp().getNormalCompletions());
	}

	public int getTotalCompletion() {
		return lev.getCompletions() + eow.getCompletions() + sos.getCompletions() + lw.getCompletions() + sp.getCompletions();
	}

	public Activity getEow() {
		return eow;
	}

	public Activity getSos() {
		return sos;
	}

	public Activity getLev() {
		return lev;
	}
	
	public Activity getLw() {
	        return lw;
	}
	
	public Activity getSp() {
	        return sp;
	}
	

	public void setEow(Activity eow) {
		this.eow = eow;
	}

	public void setSos(Activity sos) {
		this.sos = sos;
	}

	public void setLev(Activity lev) {
		this.lev = lev;
	}
	
	public void setLw(Activity lw) {
	        this.lw = lw;
	}
	
	public void setSp(Activity sp) {
	        this.sp = sp;
	}

	public String printStats() {
		return String.format
		        ("Leviathan Normal: %d\nLeviathan Prestige: %d"
		                + "\nEater of Worlds Normal: %d\nEater of Worlds Prestige: %d"
		                + "\nSpire of Stars Normal: %d\nSpire of Stars Prestige: %d"
		                + "\nLast Wish: %d"
		                + "\nScourge of the Past: %d"
		                + "\nTotal: %d", 
		        lev.getNormalCompletions(), lev.getPrestigeCompletions(),
			eow.getNormalCompletions(), eow.getPrestigeCompletions(), 
			sos.getNormalCompletions(), sos.getPrestigeCompletions(), 
			lw.getNormalCompletions(), sp.getNormalCompletions(),
			getTotalCompletion());
	}
}
