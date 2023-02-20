package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadFromLightningCreeper implements Listener {

    @EventHandler
    public void PlayerDeath(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.CREEPER) return;
        if (e.getEntityType() != EntityType.PLAYER) return;
        Creeper creeper = (Creeper) e.getDamager();
        Player p = (Player) e.getEntity();

        if (!creeper.isPowered()) return;
        Location location = p.getLocation();

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);

        item.setItemMeta(meta);

        location.getWorld().dropItemNaturally(location, item);
    }
}