package com.mronlinecoder.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UndoXCommand extends GalaxyCommand {

	public UndoXCommand() {
		super("/undox <player> <timespan>", 2, "undox");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String target = args[0];
		String timespan = args[1];
		
		
		server.dispatchCommand(server.getConsoleSender(), "coreprotect rollback u:"+target+" t:"+timespan);
		sender.sendMessage(ChatColor.GRAY+"Changes made by "+ChatColor.GREEN+target+ChatColor.GRAY+" have been undone.");
	}

}