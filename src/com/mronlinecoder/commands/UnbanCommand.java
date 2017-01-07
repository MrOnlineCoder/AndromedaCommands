package com.mronlinecoder.commands;

import java.util.logging.Logger;

import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;

public class UnbanCommand extends GalaxyCommand {

	public UnbanCommand() {
		super("/unban <player>", 1, "unban");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String target = args[0];
		String issuer = "Console";
		
		if (sender instanceof Player){			
			Player p = (Player) sender;
			issuer = p.getName();
		}
		
		if (!GalaxyPlayer.exists(target)) {
			sender.sendMessage(ChatColor.GRAY+"No info about that player!");
			return;
		}
		
		GalaxyPlayer pl = GalaxyPlayer.load(target);
		pl.ban(false);
		pl.save();
	
		server.getBanList(BanList.Type.NAME).pardon(target);
		server.broadcastMessage(ChatColor.GREEN+target+ChatColor.GRAY+" was unbanned by "+ChatColor.GREEN+issuer);
		Logger.getLogger("AndromedaCore").info("Unban: Player "+issuer+" unbanned player "+target);
	}

}
