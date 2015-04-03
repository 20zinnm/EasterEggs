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
				for (Long id : EasterEggs.drops.keySet()) {
					sender.sendMessage(ChatColor.GREEN
							+ "["
							+ id.toString()
							+ "] "
							+ EasterEggs.drops.get(id).getType().name()
									.toString() + ", x"
							+ EasterEggs.drops.get(id).getAmount());
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("all")) {
					EasterEggs.drops.clear();
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.REMOVED_DROP.toString());
				} else {
					try {
						EasterEggs.drops.remove(Long.parseLong(args[0]));
						sender.sendMessage(Lang.TITLE.toString()
								+ Lang.REMOVED_DROP.toString());
					} catch (NumberFormatException e) {
						sender.sendMessage(Lang.TITLE.toString()
								+ Lang.NUMBER_ONLY.toString());
					} catch (Exception e) {
						sender.sendMessage(Lang.TITLE.toString()
								+ Lang.INVALID_ARGS.toString());
					}
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
