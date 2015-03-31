package com.gmail.meyerzinn.eastereggs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.meyerzinn.eastereggs.EasterEggs;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class RemoveDropCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("eastereggs.removedrop")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.AQUA + "Drops: ");
				for (int i = 0; i < EasterEggs.drops.size(); i++) {
					sender.sendMessage("["
							+ (i + 1)
							+ "] "
							+ EasterEggs.drops.get(i).getType().name()
									.toString() + ", x"
							+ EasterEggs.drops.get(i).getAmount());
				}
			} else if (args.length == 1) {
				try {
					Integer indexN = Integer.parseInt(args[0]);
					EasterEggs.drops.remove(indexN - 1);
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.REMOVED_DROP.toString());
				} catch (NumberFormatException e) {
					sender.sendMessage(Lang.TITLE.toString() + Lang.NUMBER_ONLY.toString());
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
