package com.mronlinecoder.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mronlinecoder.AndromedaCommands;

public class GenCommand extends GalaxyCommand {
	AndromedaCommands plugin;
	Server serv;
	
	public GenCommand(AndromedaCommands pl) {
		super("/gen <worldname>", 1, "gen");
		this.plugin = pl;
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		final String world = args[0];
		String issuer = "Console";
		
		serv = server;
		
		if (sender instanceof Player) {
			issuer = ((Player) sender).getName();
		}
		
		serv.dispatchCommand(serv.getConsoleSender(), "mv create "+world+" normal -t flat -a false");
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				setFlags(world);
			}
		}, 60);
		server.broadcastMessage(ChatColor.GREEN+issuer+ChatColor.GRAY+" created a new world "+ChatColor.GREEN+world);
	}
	
	
	
	public void setFlags(String world) {
		
		serv.dispatchCommand(serv.getConsoleSender(), "mv modify set mode creative "+world);
		serv.dispatchCommand(serv.getConsoleSender(), "mv modify set animals false "+world);
		serv.dispatchCommand(serv.getConsoleSender(), "mv modify set monsters false "+world);
	}

}
