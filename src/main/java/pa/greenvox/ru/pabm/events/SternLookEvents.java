package pa.greenvox.ru.pabm.events;

import com.destroystokyo.paper.event.inventory.PrepareGrindstoneEvent;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SternLookEvents implements Listener {

    private static final List<Material> Helmets = Arrays.asList(
            Material.LEATHER_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.IRON_HELMET,
            Material.GOLDEN_HELMET,
            Material.DIAMOND_HELMET,
            Material.NETHERITE_HELMET
    );

    public static ItemStack applySternLook(ItemStack itemStack) {
        NBTItem i = new NBTItem(itemStack);
        i.setBoolean("sternLook", true);

        ItemStack helmet = i.getItem();
        ItemMeta meta = helmet.getItemMeta();

        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Пронзающий взгляд" + ChatColor.WHITE));

        helmet.setItemMeta(meta);

        return helmet;
    }

    @EventHandler
    public void OnEntityTargetEntity(EntityTargetEvent e) {
        if (e.getEntity().getType() != EntityType.ENDERMAN) return;
        if (e.getTarget() == null) return;
        if (e.getTarget().getType() != EntityType.PLAYER) return;
        if (e.getReason() != EntityTargetEvent.TargetReason.CLOSEST_PLAYER) return;

        Player p = (Player) e.getTarget();

        ItemStack helmet = p.getInventory().getHelmet();
        if (helmet == null) return;

        NBTItem item = new NBTItem(helmet);
        boolean hasSternLook = item.getBoolean("sternLook");

        if (hasSternLook)
            e.setCancelled(true);
    }

    @EventHandler
    public void OnGrindstoneEvent(PrepareGrindstoneEvent e) {
        ItemStack s = e.getResult();
        if (s == null) return;

        NBTItem item = new NBTItem(s);

        if (item.getBoolean("sternLook"))
            item.setBoolean("sternLook", false);

        ItemStack helmet = item.getItem();
        ItemMeta meta = helmet.getItemMeta();

        List<String> lore = meta.getLore();
        if (lore == null) return;
        lore.set(0, null);

        meta.setLore(lore);
        helmet.setItemMeta(meta);

        e.setResult(helmet);
    }
}