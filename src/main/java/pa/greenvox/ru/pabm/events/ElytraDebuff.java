package pa.greenvox.ru.pabm.events;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ElytraDebuff implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerMoveWithElytra(PlayerMoveEvent e) {
        if (!e.getPlayer().isGliding()) return;
        if (e.getPlayer().getWorld().getEnvironment() != World.Environment.THE_END) return;

        e.getPlayer().setGliding(false);
        e.getPlayer().getWorld().spawnParticle(Particle.REDSTONE, e.getPlayer().getLocation(), 4, new Particle.DustOptions(Color.BLACK, 1));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerUseFirework(PlayerElytraBoostEvent e) {
        if (e.getPlayer().getWorld().getEnvironment() != World.Environment.THE_END) return;

        e.setCancelled(true);
    }
}
