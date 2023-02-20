package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BlackDyeCraft implements Listener {

    @EventHandler
    public void PlayerInteraction(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction().isLeftClick()) return;
        if (!e.getPlayer().isSneaking()) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock().getType() != Material.WATER_CAULDRON) return;

        Levelled levelled = (Levelled) e.getClickedBlock().getBlockData();

        if (levelled.getLevel() == 0) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.COAL &&
            e.getPlayer().getInventory().getItemInMainHand().getType() != Material.CHARCOAL) return;

        e.getPlayer().getInventory().setItemInMainHand(e.getPlayer().getInventory().getItemInMainHand().subtract());

        Location l = e.getClickedBlock().getLocation();

        if (levelled.getLevel() == 1) {
            e.getPlayer().getWorld().getBlockAt(l).setType(Material.CAULDRON);
        } else {
            levelled.setLevel(levelled.getLevel() - 1);

            e.getPlayer().getWorld().getBlockAt(l).setBlockData(levelled);
        }

        e.getPlayer().getWorld().dropItemNaturally(l.toCenterLocation(), new ItemStack(Material.BLACK_DYE));

        e.getPlayer().getWorld().spawnParticle(Particle.REDSTONE, l.toCenterLocation().add(0, 0.75, 0), 0, new Particle.DustOptions(Color.BLUE, 2));

        e.getPlayer().getWorld().playSound(l, Sound.ITEM_BUCKET_EMPTY, 1, 0);
    }
}
