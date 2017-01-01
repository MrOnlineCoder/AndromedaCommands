package com.mronlinecoder.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;
import com.mronlinecoder.Rank;

public class RankCommand extends GalaxyCommand {
	FileConfiguration cfg;
	Rank ranks = new Rank();
	
	public RankCommand(FileConfiguration config) {
		super("/rank <player> <rank> [reason]", 2, "rank");
		this.cfg = config;
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String rankReason = "No reason given";
		String target = args[0];
		String rank = args[1];
		String issuer = "Console";
		
		if (args.length > 2) {
			rankReason = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
		}
		
		if (!GalaxyPlayer.exists(target)) {
			sender.sendMessage(ChatColor.GRAY+"There is no player with that name!");
			return;
		}
		
		if (sender instanceof Player){			
			GalaxyPlayer gIssuer = GalaxyPlayer.load(((Player) sender).getName());
			issuer = gIssuer.getName();
			if (!ranks.isLower(rank, cfg.getString("ranks."+ranks.getRankName(issuer)+".canRank"))) {
				sender.sendMessage(ChatColor.GRAY+"You can't give so high rank!");
				return;
			}
			
			gIssuer.setPromotes(1);
			gIssuer.save();
		}
		
		GalaxyPlayer gTarget = GalaxyPlayer.load(target);
		gTarget.setRankChanger(issuer);
		gTarget.setRankReason(rankReason);
		gTarget.save();
		
		server.dispatchCommand(server.getConsoleSender(), "pex user "+target+" group set "+rank);
		server.broadcastMessage(ChatColor.GREEN+issuer+ChatColor.GRAY+" changed "+ChatColor.GREEN+target+ChatColor.GRAY+"'s rank to "+ChatColor.GREEN+rank+ChatColor.GRAY+":");
		server.broadcastMessage(ChatColor.GRAY+rankReason);
		
	}

}
