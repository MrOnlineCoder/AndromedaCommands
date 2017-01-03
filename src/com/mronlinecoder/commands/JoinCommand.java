package com.mronlinecoder.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand extends GalaxyCommand {

	public JoinCommand() {
		super("/join <world>", 1, "join");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"Only for in-game players!");
			return;
		}
		
		Player pl = (Player) sender;
		
		String world = args[0];
		
		/*if (!sender.hasPermission("andromeda.worlds."+world)) {
			sender.sendMessage(ChatColor.GRAY+"You do not have permission to join that world!");
			return;
		}*/
		
		sender.sendMessage(ChatColor.GRAY+"Joining world "+ChatColor.GREEN+world);
		server.dispatchCommand(server.getConsoleSender(), "mv tp "+pl.getName()+" "+world);
	}

}
