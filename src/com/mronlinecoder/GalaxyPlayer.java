package com.mronlinecoder;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class GalaxyPlayer {
	public String name;
	public int logins;
	public int kicks;
	public int promotes; //That also counts demotes
	public int bans;
	public String rankReason;
	public String rankChanger;
	public int time;
	public boolean isBanned;
	public static final String folderName = "plugins/Andromeda/players/";
	
	public static GalaxyPlayer load(String name) {
		File f = new File(folderName+name+".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GalaxyPlayer temp = new GalaxyPlayer(name, 0, 0, 0, 0, "Welcome to our server!", "<Console>", 0, false);
			temp.save();
			return temp;
		}
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		GalaxyPlayer temp = new GalaxyPlayer(name, yml.getInt("logins"), yml.getInt("kicks"), yml.getInt("promotes"), yml.getInt("bans"), yml.getString("rankReason"), yml.getString("rankChanger"), yml.getInt("time"), yml.getBoolean("isBanned"));
		return temp;
	}
	
	public static boolean exists(String name) {
		return new File(folderName+name+".yml").exists();
	}
	
	public boolean save() {
		File f = new File(folderName+name+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		yml.set("logins", logins);
		yml.set("kicks", kicks);
		yml.set("promotes", promotes);
		yml.set("bans", bans);
		yml.set("rankChanger", rankChanger);
		yml.set("rankReason", rankReason);
		yml.set("time", time);
		yml.set("isBanned", isBanned);
		try {
			yml.save(f);
		} catch(IOException e) {
			// Handle any IO exception here
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLogins() {
		return logins;
	}

	public void setLogins(int delta) {
		this.logins += delta;
	}

	public int getKicks() {
		return kicks;
	}

	public void setKicks(int delta) {
		this.kicks += kicks;
	}

	public int getPromotes() {
		return promotes;
	}

	public void setPromotes(int delta) {
		this.promotes += delta;
	}

	public String getRankReason() {
		return rankReason;
	}

	public void setRankReason(String rankReason) {
		this.rankReason = rankReason;
	}

	public String getRankChanger() {
		return rankChanger;
	}

	public void setRankChanger(String rankChanger) {
		this.rankChanger = rankChanger;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int delta) {
		this.time += delta;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void ban(boolean isBanned) {
		this.isBanned = isBanned;
	}
	
	public void setBans(int delta) {
		this.bans += delta;
	}

	public int getBans() {
		return bans;
	}

	public GalaxyPlayer(String name, int logins, int kicks, int promotes,
			int bans, String rankReason, String rankChanger, int time,
			boolean isBanned) {
		this.name = name;
		this.logins = logins;
		this.kicks = kicks;
		this.promotes = promotes;
		this.bans = bans;
		this.rankReason = rankReason;
		this.rankChanger = rankChanger;
		this.time = time;
		this.isBanned = isBanned;
	}
}
