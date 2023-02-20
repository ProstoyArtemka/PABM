package pa.greenvox.ru.pabm.events;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenseHorn implements Listener {

    private Sound getSound(String name) {

        switch (name) {
            case "minecraft:ponder_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_0;
            case "minecraft:sing_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_1;
            case "minecraft:seek_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_2;
            case "minecraft:feel_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_3;
            case "minecraft:admire_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_4;
            case "minecraft:call_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_5;
            case "minecraft:yearn_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_6;
            case "minecraft:dream_goat_horn":
                return Sound.ITEM_GOAT_HORN_SOUND_7;
        }

        return Sound.ITEM_GOAT_HORN_SOUND_0;
    }

    @EventHandler
    public void HornDispense(BlockDispenseEvent e) {
        if (e.getItem().getType() != Material.GOAT_HORN) return;

        NBTItem item = new NBTItem(e.getItem());
        World w = e.getBlock().getWorld();
        Location l = e.getBlock().getLocation();
        Dispenser d = (Dispenser) e.getBlock().getBlockData();
        Location dl = l.toCenterLocation().add(d.getFacing().getDirection().multiply(1.2));

        String sound = item.getString("instrument");

        w.playSound(e.getBlock().getLocation(), getSound(sound), 1, 1);
        w.spawnParticle(Particle.NOTE, dl, 0);

        e.setCancelled(true);
    }

}
