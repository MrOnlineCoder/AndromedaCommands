package com.mronlinecoder.commands;

import java.util.ArrayList;
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

public class BanXCommand extends GalaxyCommand {

	FileConfiguration cfg;
	Rank rank = new Rank();
	
	ArrayList<String> confirmations = new ArrayList<>();
	
	public BanXCommand(FileConfiguration config) {
		super("/banx <player>", 1, "banx");
		this.cfg = config;
	}
	
	public void showWarning(CommandSender sender) {
		sender.sendMessage(ChatColor.GRAY+"WARNING: This command will ban player and undo almost ALL player's actions. ");
		sender.sendMessage(ChatColor.GRAY+"This action cannot be undone.");
		sender.sendMessage(ChatColor.GRAY+"Type "+ChatColor.GREEN+"/banx <player> confirm"+ChatColor.GRAY+" to confirm.");
	}

	@Override
	public void run(Server server, CommandSender sender, String[] args) {
		String banReason = "Banned for grief / blockspam";
		String reasonText;
		String target = args[0];
		String issuer = "Console";
		
		
		if (sender instanceof Player) {
			Player ipl = (Player) sender;
			issuer = ipl.getName();
			if (GalaxyPlayer.exists(target) && !rank.isLower(rank.getRankName(target), cfg.getString("ranks."+rank.getRankName(issuer)+".canBan"))) {
				ipl.sendMessage(ChatColor.GRAY+"You can't ban that player!");
				return;
			}
		}
		
		if (target.equals(issuer)) {
			sender.sendMessage(ChatColor.GRAY+"You can't ban yourself.");
			return;
		}
		
		if (!confirmations.contains(issuer)) {
			confirmations.add(issuer);
			showWarning(sender);
			return;
		} else {
			if (args.length < 2) {
				showWarning(sender);
				return;
			}
			
			if (!args[1].equalsIgnoreCase("confirm")) {
				showWarning(sender);
				return;
			} 
			
			reasonText = "BanX'd by "+issuer+": "+banReason;
			
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
			
			//The undo itself. 255 weeks is 4 years, so I think it is enough
			server.dispatchCommand(server.getConsoleSender(), "coreprotect rollback u:"+target+" r:#global t:255w");
			
			server.broadcastMessage(ChatColor.RED+target+" was BanX'd by "+issuer);
			server.getBanList(BanList.Type.NAME).addBan(target, banReason, null, issuer);
			confirmations.remove(issuer);
		}

	}
}
