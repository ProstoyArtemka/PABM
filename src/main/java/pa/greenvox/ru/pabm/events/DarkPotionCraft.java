package pa.greenvox.ru.pabm.events;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

public class DarkPotionCraft implements Listener {

    @EventHandler
    public void SculkSpreads(BlockSpreadEvent e) {
        if (e.getSource().getType() != Material.SCULK_CATALYST) return;

        Block b = e.getSource();
        World w = b.getWorld();
        Location spawnLocation = b.getLocation().toCenterLocation().add(0, 0.5,0);

        for (Entity entity : w.getEntities()) {
            if (entity.getType() != EntityType.DROPPED_ITEM) continue;

            if (entity.getLocation().toCenterLocation().distance(spawnLocation) > 2) continue;
            Item i = (Item) entity;

            if (i.getItemStack().getType() != Material.POTION) continue;

            PotionMeta meta = (PotionMeta) i.getItemStack().getItemMeta();
            if (meta.getBasePotionData().getType() != PotionType.WATER) continue;

            i.remove();

            ItemStack darkPotion = new ItemStack(Material.POTION, 1);

            NBTItem nbt = new NBTItem(darkPotion);

            NBTCompoundList compoundList = nbt.getCompoundList("CustomPotionEffects");
            NBTCompound compound = compoundList.addCompound();

            compound.setInteger("Duration", 600);
            compound.setInteger("Id", 33);

            nbt.setInteger("CustomPotionColor", 0);
            NBTCompound display = nbt.getOrCreateCompound("display");
            display.setString("Name", "{\"text\":\"Зелье тьмы\",\"italic\":false}");

            darkPotion = nbt.getItem();

            w.dropItemNaturally(spawnLocation, darkPotion);

            w.playSound(e.getSource().getLocation(), Sound.BLOCK_SCULK_CHARGE, 1, 0);
        }
    }
}
