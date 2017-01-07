package com.mronlinecoder.commands;

import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;
import com.mronlinecoder.Rank;

public class KickCommand extends GalaxyCommand{
	FileConfiguration cfg;
	Rank rank = new Rank();
	
	public KickCommand(FileConfiguration config) {
		super("/kick <player> [reason]", 1, "kick");
		this.cfg = config;
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String kickReason = "You have been kicked!";
		String reasonText;
		String target = args[0];
		String issuer = "Console";
		
		if (args.length > 1) {
			kickReason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
		}
		
		reasonText = "Kicked by "+issuer+": "+kickReason;
		Player pl = server.getPlayer(target);
		if (pl == null) {
			sender.sendMessage(ChatColor.GRAY+"Target player is offline!");
			return;
		}
		
		if (sender instanceof Player) {
			Player ipl = (Player) sender;
			issuer = ipl.getName();
			if (!rank.isLower(rank.getRankName(target), cfg.getString("ranks."+rank.getRankName(issuer)+".canKick"))) {
				ipl.sendMessage(ChatColor.GRAY+"You can't kick that player!");
				return;
			}
		}
		
		if (target.equals(issuer)) {
			sender.sendMessage(ChatColor.GRAY+"You can't kick yourself. Say \"kick me\" to get kicked.");
			return;
		}
		
		
		GalaxyPlayer gIssuer = GalaxyPlayer.load(issuer);
		gIssuer.setKicks(1);
		gIssuer.save();
		
		pl.kickPlayer(reasonText);
		
		server.broadcastMessage(ChatColor.RED+target+" was kicked by "+issuer+": " + kickReason);
		Logger.getLogger("AndromedaCore").info("Kick: Player "+target+" was kicked by "+issuer+": "+kickReason);
	}
}