package pa.greenvox.ru.pabm.database;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Database {

    private static Plugin plugin;

    public static Database Instance;

    public Database(Plugin p) {
        plugin = p;

        Instance = this;

        File f = new File(p.getDataFolder() + "/database.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getBlockKey(Block b) {
        return b.getX() + "_" + b.getY() + "_" + b.getZ() + "_" + b.getWorld().getName();
    }
    public static Block getBlockFromString(String s) {
        String[] params = s.split("_");

        int x = Integer.parseInt(params[0]);
        int y = Integer.parseInt(params[1]);
        int z = Integer.parseInt(params[2]);

        World w = Bukkit.getWorld(params[3]);

        return w.getBlockAt(x, y, z);
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/database.yml"));
    }

    public void saveConfig(FileConfiguration config) {
        try {
            File f = new File(plugin.getDataFolder() + "/database.yml");
            config.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
