package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import pa.greenvox.ru.pabm.PABM;

import java.util.HashMap;
import java.util.Map;

public class CrackedBricksCraft implements Listener {

    private static final HashMap<Material, Material> Bricks = new HashMap<>();
    static {
        Bricks.put(
                Material.STONE_BRICKS,
                Material.CRACKED_STONE_BRICKS
        );

        Bricks.put(
                Material.DEEPSLATE_BRICKS,
                Material.CRACKED_DEEPSLATE_BRICKS
        );

        Bricks.put(
                Material.DEEPSLATE_TILES,
                Material.CRACKED_DEEPSLATE_TILES
        );

        Bricks.put(
                Material.NETHER_BRICKS,
                Material.CRACKED_NETHER_BRICKS
        );

        Bricks.put(
                Material.POLISHED_BLACKSTONE_BRICKS,
                Material.CRACKED_POLISHED_BLACKSTONE_BRICKS
        );
    }


    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getPlayer().isSneaking()) return;

        if (!Bricks.containsKey(e.getClickedBlock().getType())) return;

        if (!e.getAction().isRightClick()) return;

        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack secondHandItem = e.getPlayer().getInventory().getItemInOffHand();

        if (secondHandItem.getType() != e.getClickedBlock().getType()) return;

        if (handItem.getType() != Material.WOODEN_PICKAXE &&
            handItem.getType() != Material.STONE_PICKAXE &&
            handItem.getType() != Material.GOLDEN_PICKAXE &&
            handItem.getType() != Material.IRON_PICKAXE &&
            handItem.getType() != Material.DIAMOND_PICKAXE &&
            handItem.getType() != Material.NETHERITE_PICKAXE) return;

        Damageable d = (Damageable) handItem.getItemMeta();

        d.setDamage(d.getDamage() + 2);

        if (d.getDamage() > handItem.getType().getMaxDurability())
            handItem = new ItemStack(Material.AIR);
        else
            handItem.setItemMeta(d);

        e.getPlayer().getInventory().setItemInMainHand(handItem);

        PABM.Instance.getCoreProtect().logRemoval(e.getPlayer().getName(),
                e.getClickedBlock().getLocation(),
                e.getClickedBlock().getType(),
                e.getClickedBlock().getBlockData());

        PABM.Instance.getCoreProtect().logPlacement(e.getPlayer().getName(),
                e.getClickedBlock().getLocation(),
                Bricks.get(e.getClickedBlock().getType()),
                e.getClickedBlock().getBlockData());

        e.getPlayer().swingMainHand();
        e.getClickedBlock().setType(Bricks.get(e.getClickedBlock().getType()));

        e.getPlayer().getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().toCenterLocation().add(e.getBlockFace().getDirection()), 5, e.getClickedBlock().getBlockData());
        e.getPlayer().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_STONE_BREAK, 1, 0);

        e.setCancelled(true);
    }
}
