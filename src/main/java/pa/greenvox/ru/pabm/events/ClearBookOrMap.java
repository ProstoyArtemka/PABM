package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClearBookOrMap implements Listener {

    private void cleanItem(Player p, ItemStack item, Levelled cauldron, Location l) {
        p.getWorld().dropItemNaturally(l.toCenterLocation(), item);
        p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

        if (cauldron.getLevel() == 1) {
            p.getWorld().getBlockAt(l).setType(Material.CAULDRON);
        } else {
            cauldron.setLevel(cauldron.getLevel() - 1);

            p.getWorld().getBlockAt(l).setBlockData(cauldron);
        }

        p.getWorld().spawnParticle(Particle.REDSTONE, l.toCenterLocation().add(0, 0.75, 0), 0, new Particle.DustOptions(Color.BLUE, 2));

        p.getWorld().playSound(l, Sound.ITEM_BUCKET_EMPTY, 1, 0);
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction().isLeftClick()) return;
        if (!e.getPlayer().isSneaking()) return;

        if (e.getClickedBlock().getType() != Material.WATER_CAULDRON) return;

        Levelled levelled = (Levelled) e.getClickedBlock().getBlockData();

        if (levelled.getLevel() == 0) return;

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

        if (item.getType() == Material.WRITTEN_BOOK)
            cleanItem(e.getPlayer(), new ItemStack(Material.WRITABLE_BOOK), levelled, e.getClickedBlock().getLocation());
        if (item.getType() == Material.FILLED_MAP)
            cleanItem(e.getPlayer(), new ItemStack(Material.MAP), levelled, e.getClickedBlock().getLocation());
    }

}
