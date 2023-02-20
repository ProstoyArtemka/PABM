package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.Arrays;

public class MapAuthor implements Listener {

    @EventHandler
    public void OnPlayerCreateMap(PlayerInteractEvent e) {
        if (e.getHand() == null) return;

        ItemStack hand = e.getPlayer().getInventory().getItem(e.getHand());
        if (hand.getType() != Material.MAP) return;

        e.setCancelled(true);

        MapView view = Bukkit.createMap(e.getPlayer().getWorld());

        view.setScale(MapView.Scale.NORMAL);
        view.setTrackingPosition(true);

        view.setCenterX((int) e.getPlayer().getLocation().getX());
        view.setCenterZ((int) e.getPlayer().getLocation().getZ());

        ItemStack item = new ItemStack(Material.FILLED_MAP);

        MapMeta meta = (MapMeta) item.getItemMeta();
        meta.setMapView(view);
        meta.setLore(Arrays.asList("", ChatColor.GRAY + "Автор: " + e.getPlayer().getName()));

        item.setItemMeta(meta);

        if (hand.getAmount() == 1) hand = new ItemStack(Material.AIR);
        else hand = hand.subtract();

        e.getPlayer().getInventory().setItem(e.getHand(), hand);
        e.getPlayer().getInventory().addItem(item);
    }
}