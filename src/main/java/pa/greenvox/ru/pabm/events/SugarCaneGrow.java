package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SugarCaneGrow implements Listener {

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (!e.getAction().isRightClick()) return;
        World w = e.getPlayer().getWorld();

        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        if (handItem.getType() != Material.BONE_MEAL) return;

        Block b = e.getClickedBlock();
        if (b.getType() != Material.SUGAR_CANE) return;

        Block bDown = w.getBlockAt(b.getLocation().add(0, -1, 0));
        if (bDown.getType() != Material.SAND) return;

        Location setBlockL = b.getLocation();

        for (int i = 0; i < 3; i++) {
            while (w.getBlockAt(setBlockL).getType() != Material.AIR) {
                setBlockL.add(0, 1, 0);

                if (setBlockL.getY() > 254) return;
            }

            if (w.getBlockAt(setBlockL).getType() == Material.AIR) {
                w.setType(setBlockL, Material.SUGAR_CANE);

                setBlockL.add(0, 1, 0);
            }
        }

        e.getPlayer().swingMainHand();
        e.getPlayer().getInventory().setItemInMainHand(handItem.subtract());

        w.spawnParticle(Particle.VILLAGER_HAPPY, b.getLocation().toCenterLocation(), 5);

        e.getPlayer().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ITEM_BONE_MEAL_USE, 1, 0);
    }
}