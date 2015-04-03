package com.gmail.meyerzinn.eastereggs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.meyerzinn.eastereggs.EasterEggs;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class ListDropsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("eastereggs.listdrops")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.AQUA + "Drops: ");
				for (Long id : EasterEggs.drops.keySet()) {
					sender.sendMessage(ChatColor.GREEN + "["
							+ id.toString()
							+ "] "
							+ EasterEggs.drops.get(id).getType().name().toString() + ", x"
							+ EasterEggs.drops.get(id).getAmount());
				}
			} else {
				sender.sendMessage(Lang.TITLE.toString()
						+ Lang.INVALID_ARGS.toString());
			}
		} else {
			sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
		}
		return true;
	}

}
