package pa.greenvox.ru.pabm.items;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

public class Handcuffs extends ItemStack {

    public Handcuffs() {
        super(Material.CARROT_ON_A_STICK);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("Наручники");

        this.setItemMeta(meta);

        NBTItem nbt = new NBTItem(this);
        nbt.setBoolean("IsHandCuffs", true);

        this.setItemMeta(nbt.getItem().getItemMeta());
    }

    @Nullable
    public static Handcuffs getHandcuffs(ItemStack item) {
        if (item.getType() != Material.CARROT_ON_A_STICK) return null;
        NBTItem nbt = new NBTItem(item);

        if (nbt.hasKey("IsHandCuffs")) return new Handcuffs();
        return null;
    }
}
