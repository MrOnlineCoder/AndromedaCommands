package com.mronlinecoder.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mronlinecoder.GalaxyPlayer;
import com.mronlinecoder.Rank;

public class BanCommand extends GalaxyCommand{
	FileConfiguration cfg;
	Rank rank = new Rank();
	
	public BanCommand(FileConfiguration config) {
		super("/ban <player> [reason]", 1, "ban");
		this.cfg = config;
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String banReason = "You have been banned!";
		String reasonText;
		String target = args[0];
		String issuer = "Console";
		
		if (args.length > 1) {
			banReason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
		}
		
		if (sender instanceof Player) {
			Player ipl = (Player) sender;
			issuer = ipl.getName();
			if (GalaxyPlayer.exists(target) && !rank.isLower(rank.getRankName(target), cfg.getString("ranks."+rank.getRankName(issuer)+".canBan"))) {
				ipl.sendMessage(ChatColor.GRAY+"You can't ban that player!");
				return;
			}
		}
		
		reasonText = "Banned by "+issuer+": "+banReason;
		
		Player pl = server.getPlayer(target);
		if (pl != null) {
			pl.kickPlayer(reasonText);
		}
		
		GalaxyPlayer gTarget = GalaxyPlayer.load(target);
		gTarget.ban(true);
		gTarget.save();
		
		if (sender instanceof Player) {
			GalaxyPlayer gIssuer = GalaxyPlayer.load(issuer);
			gIssuer.setBans(1);
			gIssuer.save();
		}

		server.broadcastMessage(ChatColor.RED+target+" was banned by "+issuer+": " + banReason);
		server.getBanList(BanList.Type.NAME).addBan(target, reasonText, null, issuer);
	}

}
