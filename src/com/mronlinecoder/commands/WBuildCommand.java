package com.mronlinecoder.commands;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WBuildCommand  extends GalaxyCommand {

	public WBuildCommand() {
		super("/wbuild <rank>", 1, "wbuild");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"Only for in-game players!");
			return;
		}
		
		Player pl = (Player) sender;
		
		String rank = args[0];
		String world = pl.getWorld().getName();
		
		
		pl.sendMessage(ChatColor.GRAY+"Build rank for world "+ChatColor.GREEN+world+ChatColor.GRAY+" has been set to "+ChatColor.GREEN+rank);
		server.getPluginManager().getPlugin("Andromeda").getConfig().set("worlds."+world+".build", rank);
		Logger.getLogger("AndromedaCore").info("WBuild: Player "+pl.getName()+" changed "+world+" build level to "+rank);
	}

}
