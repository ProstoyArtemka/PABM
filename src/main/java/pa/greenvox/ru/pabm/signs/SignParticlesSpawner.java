package pa.greenvox.ru.pabm.signs;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import pa.greenvox.ru.pabm.database.Database;

public class SignParticlesSpawner implements Runnable{
    @Override
    public void run() {
        FileConfiguration configuration = Database.Instance.getConfig();

        for (String block : configuration.getStringList("signs_list")) {
            Block b = Database.getBlockFromString(block);

            b.getWorld().spawnParticle(Particle.SPELL_MOB, b.getLocation().toCenterLocation(), 2);
        }
    }
}
