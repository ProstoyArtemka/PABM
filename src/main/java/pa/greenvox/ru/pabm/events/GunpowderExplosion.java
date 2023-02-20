package pa.greenvox.ru.pabm.events;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class GunpowderExplosion implements Listener {

    @EventHandler
    public void GunpowderFires(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getHand() == null) return;
        if (e.getInteractionPoint() == null) return;
        if (e.getClickedBlock().getType() != Material.FIRE &&
            e.getClickedBlock().getType() != Material.SOUL_FIRE) return;

        ItemStack hand = e.getPlayer().getInventory().getItem(e.getHand());
        if (hand.getType() != Material.GUNPOWDER) return;

        if (hand.getAmount() == 1) hand = new ItemStack(Material.AIR);
        else hand = hand.subtract();

        e.getPlayer().getInventory().setItem(e.getHand(), hand);

        e.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getInteractionPoint(), 0);
        e.getPlayer().getWorld().playSound(e.getInteractionPoint(), Sound.ENTITY_GENERIC_EXPLODE, 0.3f, -0.2f);

        if (e.getHand() == EquipmentSlot.HAND) e.getPlayer().swingMainHand();
        else e.getPlayer().swingOffHand();
    }
}
