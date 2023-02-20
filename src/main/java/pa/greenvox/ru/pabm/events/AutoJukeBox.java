package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import pa.greenvox.ru.pabm.PABM;

public class AutoJukeBox implements Listener {

    @EventHandler
    public void DispenseMusicDisc(BlockDispenseEvent e) {
        if (!e.getItem().getTranslationKey().contains("music_disc")) return;

        ItemStack musicDisc = e.getItem();
        Dispenser d = (Dispenser) e.getBlock().getBlockData();
        Location lookBlock = e.getBlock().getLocation().toBlockLocation().add(d.getFacing().getDirection());
        Block lb = e.getBlock().getWorld().getBlockAt(lookBlock);

        if (lb.getType() != Material.JUKEBOX) return;

        Jukebox jukebox = (Jukebox) lb.getState();

        jukebox.eject();
        jukebox.setRecord(musicDisc);

        jukebox.update();

        e.setCancelled(true);

        Bukkit.getScheduler().runTaskLater(PABM.Instance, () -> {
            org.bukkit.block.Dispenser inv = (org.bukkit.block.Dispenser) e.getBlock().getState();

            inv.getInventory().removeItem(e.getItem());
        }, 1);

    }

}
