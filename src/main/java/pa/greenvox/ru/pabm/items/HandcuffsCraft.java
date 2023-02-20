package pa.greenvox.ru.pabm.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class HandcuffsCraft extends ShapedRecipe {
    public HandcuffsCraft() {
        super(NamespacedKey.fromString("handcuffs"), new Handcuffs());

        this.shape("RRR", "RRR", "RRR").setIngredient('R', Material.BARRIER);
    }
}
