package pa.greenvox.ru.pabm.events;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ShulkerColor implements Listener {

    public DyeColor getColor(Material m) {

        switch (m) {
            case RED_DYE:
                return DyeColor.RED;
            case ORANGE_DYE:
                return DyeColor.ORANGE;
            case YELLOW_DYE:
                return DyeColor.YELLOW;
            case WHITE_DYE:
                return DyeColor.WHITE;
            case MAGENTA_DYE:
                return DyeColor.MAGENTA;
            case LIGHT_BLUE_DYE:
                return DyeColor.LIGHT_BLUE;
            case LIME_DYE:
                return DyeColor.LIME;
            case PINK_DYE:
                return DyeColor.PINK;
            case GRAY_DYE:
                return DyeColor.GRAY;
            case LIGHT_GRAY_DYE:
                return DyeColor.LIGHT_GRAY;
            case CYAN_DYE:
                return DyeColor.CYAN;
            case PURPLE_DYE:
                return DyeColor.PURPLE;
            case BLUE_DYE:
                return DyeColor.BLUE;
            case BROWN_DYE:
                return DyeColor.BROWN;
            case GREEN_DYE:
                return DyeColor.GREEN;
            case BLACK_DYE:
                return DyeColor.BLACK;
        }

        return null;
    }

    @EventHandler
    public void InteractAtShulker(PlayerInteractAtEntityEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;

        DyeColor color = getColor(e.getPlayer().getInventory().getItemInMainHand().getType());
        World w = e.getPlayer().getWorld();

        if (e.getRightClicked().getType() != EntityType.SHULKER) return;
        if (color == null) return;

        Shulker s = (Shulker) e.getRightClicked();
        s.setColor(color);
        e.getPlayer().getInventory().setItemInMainHand(e.getPlayer().getInventory().getItemInMainHand().subtract());

        w.spawnParticle(Particle.REDSTONE,
                e.getRightClicked().getLocation().toCenterLocation().add(0, 1.25, 0),
                0, 0, 0, 0,
                new Particle.DustOptions(color.getColor(), 2));
        w.playSound(e.getRightClicked().getLocation(), Sound.ITEM_DYE_USE, 1, 0);
    }
}
