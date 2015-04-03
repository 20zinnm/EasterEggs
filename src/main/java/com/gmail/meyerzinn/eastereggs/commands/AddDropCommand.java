package com.gmail.meyerzinn.eastereggs.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.meyerzinn.eastereggs.EasterEggs;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class AddDropCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("eastereggs.adddrop")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					Player p = (Player) sender;
					ItemStack is = p.getItemInHand();
					if (is == null || is.getType().equals(Material.AIR)) {
						p.sendMessage(Lang.TITLE.toString() + Lang.MUST_HAVE_ITEM.toString());
					} else {
						EasterEggs.drops.put(System.currentTimeMillis(), is);
						p.sendMessage(Lang.TITLE.toString() + Lang.ADDED_DROP.toString());
					}
				} else {
					sender.sendMessage(Lang.TITLE.toString()
							+ Lang.INVALID_ARGS.toString());
				}
			} else {
				sender.sendMessage(Lang.TITLE.toString()
						+ Lang.PLAYER_ONLY.toString());
			}
		} else {
			sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
		}
		return true;
	}

}
