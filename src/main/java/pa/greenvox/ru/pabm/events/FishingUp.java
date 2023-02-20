package pa.greenvox.ru.pabm.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishingUp implements Listener {

    public static List<ItemStack> JungleDrop = Arrays.asList(
            new ItemStack(Material.BAMBOO),
            new ItemStack(Material.JUNGLE_LEAVES)
    );

    public static List<ItemStack> DesertsDrop = Arrays.asList(
            new ItemStack(Material.SAND),
            new ItemStack(Material.DEAD_BUSH),
            new ItemStack(Material.RED_SAND),
            new ItemStack(Material.STICK),
            new ItemStack(Material.GOLD_NUGGET)
    );

    public static List<ItemStack> FlowerForestDrop = Arrays.asList(
            new ItemStack(Material.DANDELION),
            new ItemStack(Material.POPPY),
            new ItemStack(Material.BLUE_ORCHID),
            new ItemStack(Material.ALLIUM),
            new ItemStack(Material.AZURE_BLUET),
            new ItemStack(Material.RED_TULIP),
            new ItemStack(Material.ORANGE_TULIP),
            new ItemStack(Material.WHITE_TULIP),
            new ItemStack(Material.PINK_TULIP),
            new ItemStack(Material.OXEYE_DAISY),
            new ItemStack(Material.CORNFLOWER),
            new ItemStack(Material.LILY_OF_THE_VALLEY)
    );

    public static List<ItemStack> MushroomFieldDrop = Arrays.asList(
            new ItemStack(Material.BROWN_MUSHROOM),
            new ItemStack(Material.RED_MUSHROOM)
    );

    public static List<Biome> Jungles = Arrays.asList(
            Biome.JUNGLE,
            Biome.BAMBOO_JUNGLE,
            Biome.SPARSE_JUNGLE
    );

    public static List<Biome> Deserts = Arrays.asList(
            Biome.DESERT,
            Biome.BADLANDS,
            Biome.ERODED_BADLANDS,
            Biome.WOODED_BADLANDS
    );

    public static List<Biome> Forests = Arrays.asList(
            Biome.FOREST,
            Biome.BIRCH_FOREST,
            Biome.DARK_FOREST,
            Biome.OLD_GROWTH_BIRCH_FOREST,
            Biome.WINDSWEPT_FOREST
    );

    public static List<Biome> Taigas = Arrays.asList(
            Biome.TAIGA,
            Biome.SNOWY_TAIGA,
            Biome.OLD_GROWTH_PINE_TAIGA,
            Biome.OLD_GROWTH_SPRUCE_TAIGA
    );

    public ItemStack getJungleDrop() {
        return JungleDrop.get(new Random().nextInt(JungleDrop.size()));
    }

    public ItemStack getDesertDrop() {
        return DesertsDrop.get(new Random().nextInt(DesertsDrop.size()));
    }

    public ItemStack getFlowerForestDrop() {
        return FlowerForestDrop.get(new Random().nextInt(FlowerForestDrop.size()));
    }

    public ItemStack getMushroomDrop() {
        return MushroomFieldDrop.get(new Random().nextInt(MushroomFieldDrop.size()));
    }

    @EventHandler
    public void OnFishGet(PlayerFishEvent e) {

        if (e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        Location l = e.getHook().getLocation();
        World w = e.getPlayer().getWorld();

        Item i = (Item) e.getCaught();
        if (i == null) return;

        if (Jungles.contains(w.getBiome(l))) {
            if (new Random().nextInt(100) > 94) i.setItemStack(getJungleDrop());
        } else if (Deserts.contains(w.getBiome(l))) {
            if (new Random().nextInt(100) > 94) i.setItemStack(getDesertDrop());
        } else if (w.getBiome(l) == Biome.FLOWER_FOREST) {
            if (new Random().nextInt(100) > 94) i.setItemStack(getFlowerForestDrop());
        } else if (w.getBiome(l) == Biome.MUSHROOM_FIELDS) {
            if (new Random().nextInt(100) > 94) i.setItemStack(getMushroomDrop());
        } else if (Forests.contains(w.getBiome(l))) {
            if (new Random().nextInt(100) > 94) i.setItemStack(new ItemStack(Material.TALL_GRASS));
        } else if (Taigas.contains(w.getBiome(l))) {
            if (new Random().nextInt(100) > 94) i.setItemStack(new ItemStack(Material.LARGE_FERN));
        }
    }
}
