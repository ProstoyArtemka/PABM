package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class ArrowsCraft implements Listener {

    @EventHandler
    public void PlayerInteracted(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction().isLeftClick()) return;
        if (e.getClickedBlock().getType() != Material.FLETCHING_TABLE) return;

        ItemStack arrowHand = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack potionHand = e.getPlayer().getInventory().getItemInOffHand();
        if (arrowHand.getType() != Material.ARROW || arrowHand.getAmount() < 16) return;
        if (potionHand.getType() != Material.POTION) return;

        PotionMeta meta = (PotionMeta) potionHand.getItemMeta();
        World w = e.getPlayer().getWorld();

        Arrow arrowEntity = w.spawnArrow(new Location(w, 0, -128, 0), new Vector(0, 0, 0), 0, 0);

        for (PotionEffect effect : meta.getCustomEffects()) {
            arrowEntity.addCustomEffect(effect, false);
        }

        arrowEntity.setBasePotionData(meta.getBasePotionData());

        ItemStack arrowStack = arrowEntity.getItemStack();
        arrowStack.setAmount(16);
        w.dropItemNaturally(e.getClickedBlock().getLocation().toCenterLocation().add(0, 1, 0), arrowStack);

        arrowEntity.remove();

        e.getPlayer().getInventory().setItemInMainHand(arrowHand.subtract(16));
        e.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));

        if (arrowEntity.getColor() == null) return;
        w.spawnParticle(Particle.REDSTONE, e.getClickedBlock().getLocation().toCenterLocation().add(0, 1.25, 0), 0, new Particle.DustOptions(arrowEntity.getColor(), 2));

        w.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 0);
    }
}
