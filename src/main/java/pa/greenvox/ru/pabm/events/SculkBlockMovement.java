package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SculkBlockMovement implements Runnable {

    @Override
    public void run() {

        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (!(e instanceof LivingEntity)) return;

                LivingEntity le = (LivingEntity) e;

                Block b = w.getBlockAt(e.getLocation().toBlockLocation().subtract(0, 1, 0));
                Block cB = w.getBlockAt(e.getLocation().toBlockLocation().subtract(0, 0, 0));

                if (b.getType() == Material.SCULK_VEIN || b.getType() == Material.SCULK ||
                    cB.getType() == Material.SCULK_VEIN || cB.getType() == Material.SCULK)
                    le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 2, true, false, false));
            }
        }
    }
}
