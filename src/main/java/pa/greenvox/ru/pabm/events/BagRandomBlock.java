package pa.greenvox.ru.pabm.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BagRandomBlock implements Listener {

    @NotNull
    public static List<Material> BlockedBlocks = new ArrayList<>();

    static {
        BlockedBlocks.addAll(Arrays.asList(
                Material.BELL,
                Material.SCULK_VEIN,
                Material.POINTED_DRIPSTONE,

                Material.AMETHYST_CLUSTER,
                Material.BUDDING_AMETHYST,
                Material.LARGE_AMETHYST_BUD,
                Material.MEDIUM_AMETHYST_BUD,
                Material.SMALL_AMETHYST_BUD,

                Material.LANTERN,
                Material.SOUL_LANTERN,

                Material.DEAD_BRAIN_CORAL_FAN,
                Material.DEAD_BUBBLE_CORAL_FAN,
                Material.DEAD_HORN_CORAL_FAN,
                Material.DEAD_FIRE_CORAL_FAN,
                Material.DEAD_TUBE_CORAL_FAN
        ));

        BagRandomBlock.BlockedBlocks.addAll(Tag.BEDS.getValues());
    }

    public static List<Player> PlayerIsAlreadyUsed = new ArrayList<>();
    private static final List<Vector> vectors = Arrays.asList(
            new Vector(1, 0, 0),
            new Vector(0, 1, 0),
            new Vector(0, 0, 1),
            new Vector(0, 0, -1),
            new Vector(0, -1, 0),
            new Vector(-1, 0, 0),
            new Vector(0, 0, 0)
    );

    private List<ItemStack> getBlocks(List<ItemStack> items) {
        ArrayList<ItemStack> blocks = new ArrayList<>();

        for (ItemStack item : items)
            if (item.getType().isBlock() && item.getType().isSolid() && !BlockedBlocks.contains(item.getType())) blocks.add(item);

        return blocks;
    }

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (!e.getAction().isRightClick()) return;
        if (!e.getPlayer().isSneaking()) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BUNDLE) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (PlayerIsAlreadyUsed.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        ItemStack bundle = e.getPlayer().getInventory().getItemInMainHand();
        BundleMeta meta = (BundleMeta) bundle.getItemMeta();
        List<ItemStack> blocks = getBlocks(meta.getItems());

        ItemStack randomBlock;

        if (blocks.size() == 0) return;
        else if (blocks.size() == 1) randomBlock = blocks.get(0);
        else
            randomBlock = blocks.get(new Random().nextInt(blocks.size()));

        e.setCancelled(true);
        Location setBlockLocation = e.getClickedBlock().getLocation().add(e.getBlockFace().getDirection());
        if (e.getClickedBlock().getWorld().getBlockAt(setBlockLocation).getType() != Material.AIR) return;

        e.getClickedBlock().getWorld().setType(setBlockLocation, randomBlock.getType());

        List<ItemStack> newList = new ArrayList<>();
        boolean itemSubbed = false;

        for (ItemStack s : meta.getItems()) {
            if (s.getType() == randomBlock.getType() && !itemSubbed) {
                if (s.getAmount() > 1) newList.add(s.subtract());

                itemSubbed = true;
            } else
                newList.add(s);
        }

        meta.setItems(newList);
        bundle.setItemMeta(meta);

        PlayerIsAlreadyUsed.add(e.getPlayer());

        e.getPlayer().swingMainHand();
    }
}
