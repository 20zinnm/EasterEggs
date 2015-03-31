package com.gmail.meyerzinn.eastereggs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.meyerzinn.eastereggs.EasterEggs;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class EggsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("eastereggs.toggle")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("allow")) {
					EasterEggs.allowEggs = true;
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.TOGGLED_ALLOWED.toString());
				} else if (args[0].equalsIgnoreCase("deny")) {
					EasterEggs.allowEggs = false;
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.TOGGLED_DENIED.toString());
				} else {
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.INVALID_ARGS.toString());
				}
				return true;
			} else if (args.length == 0) {
				if (EasterEggs.allowEggs == true) {
					EasterEggs.allowEggs = false;
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.TOGGLED_DENIED.toString());
				} else {
					EasterEggs.allowEggs = true;
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.TOGGLED_ALLOWED.toString());
				}
				return true;
			} else {
				sender.sendMessage(Lang.TITLE.toString() + Lang.INVALID_ARGS.toString());
			}
		} else {
			sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
		}
		return true;
	}

}
