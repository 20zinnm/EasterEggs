package com.gmail.meyerzinn.eastereggs.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.meyerzinn.eastereggs.EasterEggs;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class EggListener implements Listener {

	private static Random rnd = new Random();

	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Egg) {
			if (EasterEggs.allowEggs) {

				Location l = e.getEntity().getLocation();
				World w = e.getEntity().getWorld();
				if (EasterEggs.broadcast) {
					Bukkit.broadcastMessage(Lang.TITLE.toString()
							+ Lang.EGG_LAND
									.toString()
									.replace(
											"%l",
											l.getX() + ", " + l.getY() + ", "
													+ l.getZ())
									.replace("%w", w.getName()));
				}
				if (explodeYN()) {
					w.createExplosion(l.getX(), l.getY(), l.getZ(), 6, false,
							false);
				} else {
					for (Long id : EasterEggs.drops.keySet()) {
						w.dropItem(l, EasterEggs.drops.get(id));
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRightClickEvent(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
				|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (EasterEggs.allowEggs) {
				if (e.getPlayer().getItemInHand().getType()
						.equals(Material.EGG)) {
					if (e.getPlayer().hasPermission("eastereggs.launch")) {
						e.getPlayer().sendMessage(
								Lang.TITLE.toString()
										+ Lang.EGG_LAUNCH.toString());
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								Lang.TITLE.toString()
										+ Lang.NO_PERMS_EGG.toString());
					}
				}
			}
		}
	}

	public static boolean explodeYN() {
		if (EasterEggs.explode == 1.0) {
			return true;
		} else if (EasterEggs.explode == 0.0) {
			return false;
		} else {
			if (rnd.nextDouble() <= EasterEggs.explode) {
				return true;
			} else {
				return false;
			}
		}
	}

}
