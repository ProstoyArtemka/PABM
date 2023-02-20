package pa.greenvox.ru.pabm.items;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class HandcuffsEvents implements Listener {

    public static ArrayList<CuffsInteraction> Interactions = new ArrayList<>();

    @EventHandler
    public void PreRecipe(PrepareResultEvent e) {
        if (e.getResult() == null) return;

        if (Handcuffs.getHandcuffs(e.getResult()) != null) e.setResult(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void PlayerInteractOnPlayer(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) e.getRightClicked();

        if (entity.getHealth() > entity.getMaxHealth() / 2) return;
        if (Handcuffs.getHandcuffs(e.getPlayer().getInventory().getItem(EquipmentSlot.HAND)) == null &&
                Handcuffs.getHandcuffs(e.getPlayer().getInventory().getItem(EquipmentSlot.OFF_HAND)) == null) return;

        for (CuffsInteraction interaction : Interactions)
            if (interaction.Entity == entity && interaction.Player == e.getPlayer()) return;

        Interactions.add(new CuffsInteraction(e.getPlayer(), entity));
    }

    @EventHandler
    public void onInteractWithFence(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (!Tag.FENCES.isTagged(e.getClickedBlock().getType())) return;

        for (CuffsInteraction interaction : Interactions)
            if (interaction.Player == e.getPlayer())
                e.setCancelled(true);
    }

//    @EventHandler
//    public void PlayerInteractOpenInvEvent(PlayerInteractAtEntityEvent e) {
//        if (!(e.getRightClicked() instanceof LivingEntity)) return;
//        LivingEntity entity = (LivingEntity) e.getRightClicked();
//
//        boolean pass = false;
//        for (CuffsInteraction interaction : Interactions)
//            if (interaction.Entity == entity && interaction.Player == e.getPlayer()) {
//                pass = true;
//                break;
//            }
//
//        if (!pass) return;
//
//        if (!e.getPlayer().isSneaking()) return;
//        if (!(entity instanceof Player)) return;
//        Player ePlayer = (Player) entity;
//
//        e.getPlayer().openInventory(ePlayer.getInventory());
//    }
}
class CuffsInteraction {

    public Player Player;
    public LivingEntity Entity;
    public Pig subEntity;

    public CuffsInteraction(Player p, LivingEntity e) {
        this.Player = p;
        this.Entity = e;
    }

    public void Update() {
        if (Entity.getLocation().getWorld() != Player.getWorld() || Entity.isDead() || Player.isDead()) {
            HandcuffsEvents.Interactions.remove(this);

            subEntity.setLeashHolder(null);
            subEntity.remove();
        }

        Entity.setVelocity(Player.getLocation().subtract(Entity.getLocation()).multiply(0.1).toVector().multiply(new Vector(1, 1.5f, 1)));

        if (Player.getLocation().distance(Entity.getLocation()) > 6) {
            HandcuffsEvents.Interactions.remove(this);
            subEntity.setLeashHolder(null);
            subEntity.remove();

            return;
        }
        if (subEntity == null) {
            subEntity = (Pig) Player.getWorld().spawnEntity(Entity.getLocation(), EntityType.PIG);
            subEntity.setGravity(false);
            subEntity.setInvulnerable(false);
            subEntity.setCollidable(false);
            subEntity.setSilent(true);
            subEntity.setAI(false);
            subEntity.setBaby();
            subEntity.setAge(-Integer.MAX_VALUE);
            subEntity.setInvisible(true);

            subEntity.setLeashHolder(Player);
        }

        subEntity.teleport(Entity.getLocation().add(new Vector(0, 0.5f, 0)));
        subEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false), true);

        if (Handcuffs.getHandcuffs(Player.getInventory().getItem(EquipmentSlot.HAND)) == null &&
            Handcuffs.getHandcuffs(Player.getInventory().getItem(EquipmentSlot.OFF_HAND)) == null) {
            HandcuffsEvents.Interactions.remove(this);

            subEntity.setLeashHolder(null);
            subEntity.remove();
        }
    }
}
