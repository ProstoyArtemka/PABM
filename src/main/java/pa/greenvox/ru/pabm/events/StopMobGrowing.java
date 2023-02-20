package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class StopMobGrowing implements Listener {

    @EventHandler
    public void OnPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Ageable)) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();

        if (p.getInventory().getItemInMainHand().getType() != Material.POISONOUS_POTATO) return;

        ItemStack potato = p.getInventory().getItemInMainHand();

        Ageable a = (Ageable) e.getRightClicked();
        if (a.getAge() > -20) return;

        a.setAge(-Integer.MAX_VALUE);

        p.getInventory().setItemInMainHand(potato.subtract());

        p.swingMainHand();
        p.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, a.getLocation(), 4);
    }

}
