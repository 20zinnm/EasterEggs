package com.gmail.meyerzinn.eastereggs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.meyerzinn.eastereggs.commands.AddDropCommand;
import com.gmail.meyerzinn.eastereggs.commands.EggsCommand;
import com.gmail.meyerzinn.eastereggs.commands.ListDropsCommand;
import com.gmail.meyerzinn.eastereggs.commands.RemoveDropCommand;
import com.gmail.meyerzinn.eastereggs.listeners.EggListener;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class EasterEggs extends JavaPlugin {

	public static Boolean allowEggs;
	public static Boolean broadcast;
	public static Double explode;
	public static HashMap<Long, ItemStack> drops = new HashMap<Long, ItemStack>();

	Logger log = getLogger();

	public static YamlConfiguration LANG;
	public static File LANG_FILE;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		allowEggs = getConfig().getBoolean("allow-eggs");
		broadcast = getConfig().getBoolean("announce-land");
		explode = getConfig().getDouble("explode");
		loadLang();
		loadDrops();
		getCommand("eggs").setExecutor(new EggsCommand());
		getCommand("adddrop").setExecutor(new AddDropCommand());
		getCommand("removedrop").setExecutor(new RemoveDropCommand());
		getCommand("listdrops").setExecutor(new ListDropsCommand());
		Bukkit.getPluginManager().registerEvents(new EggListener(), this);
	}

	public void onDisable() {
		saveDefaultConfig();
		getConfig().set("allow-eggs", allowEggs);
		getConfig().set("announce-land", broadcast);
		getConfig().set("explode", explode);
		saveConfig();
		saveDrops();
	}

	public void loadLang() {
		File lang = new File(getDataFolder(), "lang.yml");
		if (!lang.exists()) {
			try {
				getDataFolder().mkdir();
				lang.createNewFile();
				InputStream defConfigStream = this.getResource("lang.yml");
				if (defConfigStream != null) {
					@SuppressWarnings("deprecation")
					YamlConfiguration defConfig = YamlConfiguration
							.loadConfiguration(defConfigStream);
					defConfig.save(lang);
					Lang.setFile(defConfig);
					return;
				}
			} catch (IOException e) {
				e.printStackTrace(); // So they notice
				log.severe(Lang.TITLE.toString()
						+ "Couldn't create language file.");
				log.severe(Lang.TITLE.toString()
						+ "This is a fatal error. Now disabling");
				this.setEnabled(false); // Without it loaded, we can't send them
										// messages
			}
		}
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		for (Lang item : Lang.values()) {
			if (conf.getString(item.getPath()) == null) {
				conf.set(item.getPath(), item.getDefault());
			}
		}
		Lang.setFile(conf);
		EasterEggs.LANG = conf;
		EasterEggs.LANG_FILE = lang;
		try {
			conf.save(getLangFile());
		} catch (IOException e) {
			log.severe(Lang.TITLE.toString() + "Couldn't create language file.");
			log.severe(Lang.TITLE.toString()
					+ "This is a fatal error. Now disabling");
			e.printStackTrace();
			this.setEnabled(false);
		}
	}

	public YamlConfiguration getLang() {
		return LANG;
	}

	/**
	 * Get the lang.yml file.
	 * 
	 * @return The lang.yml file.
	 */
	public File getLangFile() {
		return LANG_FILE;
	}

	public void loadDrops() {
		File f = new File(getDataFolder(), "drops.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for (String key : fc.getKeys(false)) {
			ItemStack is = fc.getItemStack(key);
			drops.put(Long.parseLong(key), is);
		}
	}

	public void saveDrops() {
		File f = new File(getDataFolder(), "drops.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for (Long id : drops.keySet()) {
			fc.set(id.toString(), drops.get(id));
		}
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
