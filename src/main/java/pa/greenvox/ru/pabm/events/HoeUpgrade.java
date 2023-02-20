package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoeUpgrade implements Listener {

    public List<Material> BlocksToDestroy = Arrays.asList(
            Material.GRASS,
            Material.TALL_GRASS,
            Material.FERN,
            Material.LARGE_FERN,

            Material.POPPY,
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY,
            Material.WITHER_ROSE,

            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM,
            Material.CRIMSON_FUNGUS,
            Material.WARPED_FUNGUS,
            Material.CRIMSON_ROOTS,
            Material.WARPED_ROOTS,
            Material.NETHER_SPROUTS,

            Material.OAK_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.BIRCH_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.ACACIA_SAPLING,
            Material.DARK_OAK_SAPLING,
            Material.SEAGRASS,

            Material.SUNFLOWER,
            Material.LILAC,
            Material.ROSE_BUSH,
            Material.PEONY,

            Material.TUBE_CORAL,
            Material.BRAIN_CORAL,
            Material.BUBBLE_CORAL,
            Material.FIRE_CORAL,
            Material.HORN_CORAL,

            Material.DEAD_TUBE_CORAL,
            Material.DEAD_BRAIN_CORAL,
            Material.DEAD_BUBBLE_CORAL,
            Material.DEAD_FIRE_CORAL,
            Material.DEAD_HORN_CORAL,

            Material.TUBE_CORAL_FAN,
            Material.BRAIN_CORAL_FAN,
            Material.BUBBLE_CORAL_FAN,
            Material.FIRE_CORAL_FAN,
            Material.HORN_CORAL_FAN,

            Material.DEAD_TUBE_CORAL_FAN,
            Material.DEAD_BRAIN_CORAL_FAN,
            Material.DEAD_BUBBLE_CORAL_FAN,
            Material.DEAD_FIRE_CORAL_FAN,
            Material.DEAD_HORN_CORAL_FAN,

            Material.POTATOES,
            Material.CARROTS,
            Material.GLOW_BERRIES,
            Material.WHEAT,
            Material.BEETROOTS
    );

    public static List<Material> Hoes = Arrays.asList(
            Material.WOODEN_HOE,
            Material.STONE_HOE,
            Material.GOLDEN_HOE,
            Material.IRON_HOE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_HOE
    );

    private Block getBlock(Location l) {
        return l.getWorld().getBlockAt(l);
    }

    @EventHandler
    public void OnPlayerBreakBlock(BlockBreakEvent e) {
        if (!BlocksToDestroy.contains(e.getBlock().getType())) return;
        if (!Hoes.contains(e.getPlayer().getInventory().getItemInMainHand().getType())) return;

        Location l = e.getBlock().getLocation().toBlockLocation();
        ItemStack hoe = e.getPlayer().getInventory().getItemInMainHand();

        Damageable d = (Damageable) hoe.getItemMeta();

        for (int x = -2; x < 2; x++) {
            for (int z = -2; z < 2; z++) {
                l.add(x, 0, z);

                if (BlocksToDestroy.contains(getBlock(l).getType())) {
                    l.getWorld().getBlockAt(l).breakNaturally(hoe);

                    l.getWorld().spawnParticle(Particle.BLOCK_CRACK, l.toCenterLocation(), 5, getBlock(l).getBlockData());

                    d.setDamage(d.getDamage() + 1);
                }

                l = e.getBlock().getLocation().toBlockLocation();
            }
        }

        hoe.setItemMeta(d);

        if (d.getDamage() > hoe.getType().getMaxDurability()) hoe = new ItemStack(Material.AIR);

        e.getPlayer().getInventory().setItemInMainHand(hoe);

        e.getPlayer().swingMainHand();
    }

}
