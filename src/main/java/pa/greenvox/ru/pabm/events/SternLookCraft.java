package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SternLookCraft implements Runnable {

    private static List<Item> HelmetsCraft = new ArrayList<>();
    private static AnimationPlayer player = null;
    private static final List<Material> Helmets = Arrays.asList(
            Material.LEATHER_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.IRON_HELMET,
            Material.GOLDEN_HELMET,
            Material.DIAMOND_HELMET,
            Material.NETHERITE_HELMET
    );

    private static void updateHelmets() {
        for (World w : Bukkit.getWorlds()) {
            if (w.getEnvironment() != World.Environment.THE_END) continue;

            HelmetsCraft.clear();

            for (Item i : w.getEntitiesByClass(Item.class)) {
                if (Helmets.contains(i.getItemStack().getType()))
                    HelmetsCraft.add(i);
            }
        }
    }

    private static boolean isDragonExplodeNear(Location l) {

        Collection<AreaEffectCloud> clouds = l.getWorld().getNearbyEntitiesByType(AreaEffectCloud.class, l, 5);

        for (AreaEffectCloud c : clouds) {
            if (c.getParticle() == Particle.DRAGON_BREATH) return true;
        }

        return false;
    }

    private static void StartAnimation(Item i) {
        Collection<Enderman> endermen = i.getLocation().getWorld().getNearbyEntitiesByType(Enderman.class, i.getLocation(), 25);
        if (endermen.size() < 3) return;
        if (player != null) return;

        ArrayList<Enderman> endermanArrayList = new ArrayList<>();
        for (Enderman e : endermen)
            if (endermanArrayList.size() < 3) endermanArrayList.add(e);

        player = new AnimationPlayer(i, endermanArrayList);
    }

    private static void ProcessAnimation() {



    }

    @Override
    public void run() {
        updateHelmets();
        for (Item i : HelmetsCraft)
            if (isDragonExplodeNear(i.getLocation()));

    }
}
class AnimationPlayer {

    public List<Enderman> Endermen;
    public Item Helmet;

    public AnimationPlayer(Item i, List<Enderman> endermen) {
        Helmet = i;
        Endermen = endermen;
    }
}