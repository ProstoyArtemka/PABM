package pa.greenvox.ru.pabm.events;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevitationArrowCraft implements Listener {

    @EventHandler
    public void ArrowAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.SHULKER_BULLET) return;
        if (e.getDamager().getType() != EntityType.ARROW) return;

        Arrow a = (Arrow) e.getDamager();

        a.clearCustomEffects();
        a.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 1), true);
        a.setColor(Color.WHITE);

        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation().toCenterLocation(), a.getItemStack());

        e.getEntity().getWorld().spawnParticle(Particle.REDSTONE,
                e.getEntity().getLocation().toCenterLocation(),
                0, 0, 0, 0,
                new Particle.DustOptions(DyeColor.WHITE.getColor(), 2));
    }

}
