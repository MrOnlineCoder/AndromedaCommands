package com.mronlinecoder.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;

public class KickCommand extends GalaxyCommand{

	public KickCommand() {
		super("/kick <player> [reason]", 1, "kick");
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
		
		if (sender instanceof Player) {
			Player ipl = (Player) sender;
			issuer = ipl.getName();
		}
		
		reasonText = "Kicked by "+issuer+": "+kickReason;
		Player pl = server.getPlayer(target);
		if (pl == null) {
			sender.sendMessage(ChatColor.GRAY+"Target player is offline!");
			return;
		}
		
		GalaxyPlayer gIssuer = GalaxyPlayer.load(issuer);
		gIssuer.setKicks(1);
		gIssuer.save();
		
		pl.kickPlayer(reasonText);
		
		server.broadcastMessage(ChatColor.RED+target+" was kicked by "+issuer+":" + kickReason);
	}
}