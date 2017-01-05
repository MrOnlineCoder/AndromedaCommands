package com.mronlinecoder.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;

public class InfoCommand  extends GalaxyCommand {

	public InfoCommand() {
		super("/info [player]", 0, "info");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String name;
		if (args.length > 0) {
			name = args[0];
		} else {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED+"That command requires 1 arg in console!");
				return;
			}
			
			Player pl = (Player) sender;
			name = pl.getName();
		}
		
		if (!GalaxyPlayer.exists(name)) {
			sender.sendMessage(ChatColor.GRAY+"No info about that player!");
			return;
		}
		
		GalaxyPlayer gp = GalaxyPlayer.load(name);
		
		sender.sendMessage(ChatColor.GRAY+"About "+ChatColor.GREEN+name+ChatColor.GRAY+": ");
		if (gp.isBanned()) sender.sendMessage(ChatColor.GRAY+"Account is "+ChatColor.RED+"banned.");
		sender.sendMessage(ChatColor.GRAY+"> Logged in "+ChatColor.GREEN+gp.getLogins()+ChatColor.GRAY+" times.");
		sender.sendMessage(ChatColor.GRAY+"> Wrote "+ChatColor.GREEN+gp.getMessages()+ChatColor.GRAY+" messages.");
		sender.sendMessage(ChatColor.GRAY+"> Kicked "+ChatColor.GREEN+gp.getKicks()+ChatColor.GRAY+", changed rank for "+ChatColor.GREEN+gp.getPromotes()+ChatColor.GRAY+" and banned "+ChatColor.GREEN+gp.getBans()+ChatColor.GRAY+" players.");
		sender.sendMessage(ChatColor.GRAY+"> Last rank change by "+ChatColor.GREEN+gp.getRankChanger()+ChatColor.GRAY+":");
		sender.sendMessage(ChatColor.GRAY+gp.getRankReason());
		int time = gp.getTime();
		
		sender.sendMessage(ChatColor.GRAY+"> Totally played "+ChatColor.GREEN+(time / 60)+ChatColor.GRAY+" hours and "+ChatColor.GREEN+(time % 60)+ChatColor.GRAY+" minutes here.");
	}

}