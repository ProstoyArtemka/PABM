package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import pa.greenvox.ru.pabm.PABM;

public class StonecutterDamage implements Runnable {

    @Override
    public void run() {

        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (!(e instanceof Damageable)) continue;
                Damageable d = (Damageable) e;

                if (w.getBlockAt(e.getLocation().toBlockLocation()).getType() == Material.STONECUTTER &&
                    w.getBlockAt(e.getLocation().toBlockLocation()).getBlockPower() > 0) d.damage(2);
            }
        }
    }
}
