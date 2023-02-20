package pa.greenvox.ru.pabm.signs;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pa.greenvox.ru.pabm.database.Database;

import java.util.List;

import static pa.greenvox.ru.pabm.database.Database.getBlockKey;

public class OnSignEvent implements Listener {

    @EventHandler
    public void OnSignInteracted(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) return;
        if (e.getClickedBlock() == null) return;
        if (!Tag.SIGNS.isTagged(e.getClickedBlock().getType()) &&
            !Tag.WALL_SIGNS.isTagged(e.getClickedBlock().getType()) &&
            !Tag.STANDING_SIGNS.isTagged(e.getClickedBlock().getType())) return;

        String key = getBlockKey(e.getClickedBlock());
        String url = Database.Instance.getConfig().getString("signs." + key);

        if (url == null) return;

        e.getPlayer().setResourcePack(url);
    }

    @EventHandler
    public void OnSignBroken(BlockBreakEvent e) {
        String key = getBlockKey(e.getBlock());

        if (Database.Instance.getConfig().contains("signs." + key)) {
            FileConfiguration cfg = Database.Instance.getConfig();

            cfg.set("signs." + key, null);
            List<String> signs = cfg.getStringList("signs_list");
            signs.remove(key);

            cfg.set("signs_list", signs);

            Database.Instance.saveConfig(cfg);
        }
    }
}
