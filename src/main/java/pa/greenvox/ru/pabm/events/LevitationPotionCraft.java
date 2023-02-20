package pa.greenvox.ru.pabm.events;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class LevitationPotionCraft implements Listener, Runnable {

    private static HashMap<Item, Integer> levitationItems = new HashMap<>();

    @EventHandler
    public void OnPlayerInteractedAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() != EntityType.SHULKER_BULLET) return;

        EquipmentSlot hand = EquipmentSlot.HAND;
        ItemStack bottle = e.getPlayer().getInventory().getItem(hand);
        if (bottle.getType() != Material.GLASS_BOTTLE) {
            hand = EquipmentSlot.OFF_HAND;
            bottle = e.getPlayer().getInventory().getItem(hand);

            if (bottle.getType() != Material.GLASS_BOTTLE) return;
        }

        if (bottle.getAmount() == 1) bottle = new ItemStack(Material.AIR);
        e.getPlayer().getInventory().setItem(hand, bottle.subtract());

        e.getRightClicked().remove();

        int random = new Random().nextInt(100);
        if (random > 75) {
            e.getPlayer().getWorld().spawnParticle(Particle.REDSTONE, e.getRightClicked().getLocation(), 0, new Particle.DustOptions(Color.WHITE, 2));

            ItemStack potion = new ItemStack(Material.POTION);

            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
            meta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 0, true, true, true), true);

            meta.setDisplayName("Зелье левитации");
            meta.setColor(Color.WHITE);
            potion.setItemMeta(meta);

            NBTItem nbt = new NBTItem(potion);

            NBTCompound display = nbt.getOrCreateCompound("display");
            display.setString("Name", "{\"text\":\"Зелье левитации\",\"italic\":false}");

            potion = nbt.getItem();

            e.getPlayer().getInventory().addItem(potion);
        } else {

            Item item = e.getPlayer().getWorld().dropItemNaturally(e.getRightClicked().getLocation(), bottle.asOne());

            levitationItems.put(item, 100);
        }
    }

    @Override
    public void run() {
        for (Item i : levitationItems.keySet()) {
            if (levitationItems.get(i) <= 0) levitationItems.remove(i);
            else {
                i.setVelocity(i.getVelocity().add(new Vector(0, 0.05, 0)));

                i.getWorld().spawnParticle(Particle.REDSTONE, i.getLocation().add(0, 0.5, 0), 4, new Particle.DustOptions(Color.WHITE, 1));

                i.getWorld().playSound(i.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 0, 0);

                levitationItems.replace(i, levitationItems.get(i) - 1);
            }
        }
    }
}