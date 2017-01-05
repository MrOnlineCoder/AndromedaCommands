package com.mronlinecoder;

import java.util.HashMap;
import java.util.List;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Rank {
	
	HashMap<String, Integer> ranks = new HashMap<>();
	
	public Rank() {
		ranks.put("Guest", 0);
		ranks.put("Builder", 1);
		ranks.put("AdvBuilder", 2);
		ranks.put("ProBuilder", 3);
		ranks.put("Engineer", 4);
		ranks.put("Architect", 5);
		ranks.put("Moderator", 6);
		ranks.put("Operator", 7);
		ranks.put("Legend", 8);
		ranks.put("Admin", 9);
		ranks.put("Retired", 10);
		ranks.put("Owner", 11);
	}

	
	public int getRank(String name) {
		 PermissionUser user = PermissionsEx.getUser(name);
		 String[] groups = user.getGroupNames();
		 return ranks.get(groups[0]);
	}
	
	public String getRankName(String name) {
		 PermissionUser user = PermissionsEx.getUser(name);
		 String[] groups = user.getGroupNames();
		 return groups[0];
	}
	
	public boolean isLower(String baseRank, String targetRank) {
		return ranks.get(baseRank) <= ranks.get(targetRank);
	}
	
	public boolean hasRank(String player, String rank) {
		return getRank(player) >= ranks.get(rank);
	}
	
	//Really 800Craft gave me this idea
	public String getVerb(String rankFrom, String rankTo) {
		return ranks.get(rankTo) > ranks.get(rankFrom) ? "promot" : "demot";
	}
}
