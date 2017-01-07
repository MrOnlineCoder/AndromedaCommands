package com.mronlinecoder.commands;

import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;
import com.mronlinecoder.Rank;

public class BringCommand  extends GalaxyCommand{
	FileConfiguration cfg;
	Rank rank = new Rank();
	
	public BringCommand(FileConfiguration config) {
		super("/bring <player>", 1, "bring");
		this.cfg = config;
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String target = args[0];
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"Only for in-game players!");
			return;
		}
		
		Player targetPl = server.getPlayer(target);
		Player issuerPl = (Player) sender;
		if (targetPl == null) {
			sender.sendMessage(ChatColor.GRAY+"Target player is offline!");
			return;
		}
		
		if (!rank.isLower(rank.getRankName(target), cfg.getString("ranks."+rank.getRankName(issuerPl.getName())+".canBring"))) {
				issuerPl.sendMessage(ChatColor.GRAY+"You can't bring that player!");
				return;
		}
		
		issuerPl.sendMessage(ChatColor.GRAY+"Teleporting...");
		targetPl.sendMessage(ChatColor.GRAY+"You have been teleported by "+ChatColor.GREEN+issuerPl.getName());
		targetPl.teleport(issuerPl.getLocation());
		Logger.getLogger("AndromedaCore").info("Bring: Player "+target+" was teleported to "+issuerPl.getName());
	}
}