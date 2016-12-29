package com.mronlinecoder.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RageQuitCommand extends GalaxyCommand {

	public RageQuitCommand() {
		super("/ragequit [reason]", 0, "ragequit");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String reason = "Ragequit";
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"Only for in-game players!");
			return;
		}
		
		Player pl = (Player) sender;
		
		if (args.length > 0) {
			reason = StringUtils.join(args, " ");
		}
		
		server.broadcastMessage(ChatColor.DARK_RED+ pl.getName()+" Ragequit from server!");
		pl.kickPlayer(reason);
	}

}
