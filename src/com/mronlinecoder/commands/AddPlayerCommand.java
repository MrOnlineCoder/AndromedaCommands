package com.mronlinecoder.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;

public class AddPlayerCommand extends GalaxyCommand{

	public AddPlayerCommand() {
		super("/addplayer <name> <rank> <time>", 3, "addplayer");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String target = args[0];
		String rank = args[1];
		int time = Integer.parseInt(args[2]);
		
		if (GalaxyPlayer.exists(target)) {
			sender.sendMessage(ChatColor.GRAY+"Player with that name already exists!");
			return;
		}
		
		GalaxyPlayer pl = GalaxyPlayer.load(target);
		pl.setTime(time);
		pl.save();
		server.dispatchCommand(server.getConsoleSender(), "rank "+target+" "+rank+" "+"~AddPlayer");
		sender.sendMessage(ChatColor.GRAY+"Player "+ChatColor.GREEN+target+ChatColor.GRAY+" has been successfully added to the database!");
	}
}