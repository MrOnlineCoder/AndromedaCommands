package com.mronlinecoder.commands;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviewCommand extends GalaxyCommand {

	public ReviewCommand() {
		super("/review", 0, "review");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"Only for in-game players!");
			return;
		}
		
		Player pl = (Player) sender;
		
		for (Player p : server.getOnlinePlayers()) {
			if (p.hasPermission("andromeda.doreview")) {
				p.sendMessage(ChatColor.GREEN+pl.getName()+ChatColor.GOLD+" would like staff to review their building.");
			}
		}
		
		pl.sendMessage(ChatColor.GREEN+pl.getName()+ChatColor.GOLD+" would like staff to review their building.");
		Logger.getLogger("AndromedaCore").info("Review: Player "+pl.getName()+" asked for a review in world "+pl.getWorld().getName());
	}

}
