package pa.greenvox.ru.pabm;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pa.greenvox.ru.pabm.database.Database;
import pa.greenvox.ru.pabm.events.*;
import pa.greenvox.ru.pabm.items.HandcuffsUpdater;
import pa.greenvox.ru.pabm.items.HandcuffsCraft;
import pa.greenvox.ru.pabm.items.HandcuffsEvents;
import pa.greenvox.ru.pabm.signs.OnSignCommand;
import pa.greenvox.ru.pabm.signs.OnSignEvent;
import pa.greenvox.ru.pabm.signs.SignParticlesSpawner;

import java.util.Arrays;
import java.util.List;

public final class PABM extends JavaPlugin {

    public static PABM Instance;
    public CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
    }

    public static final List<Listener> listeners = Arrays.asList(
            new DispenseHorn(),
            new ShulkerColor(),
            new LevitationArrowCraft(),
            new FishingUp(),
            new ArrowsCraft(),
            new DarkPotionCraft(),
            new ClearBookOrMap(),
            new BlackDyeCraft(),
            new SugarCaneGrow(),
            new CrackedBricksCraft(),
            new StopMobGrowing(),
            new HoeUpgrade(),
            new OnSignEvent(),
            new LevitationPotionCraft(),
            new BagRandomBlock(),
            new PlayerHeadFromLightningCreeper(),
            new GunpowderExplosion(),
            new ElytraDebuff(),

            new HandcuffsEvents()
    );
    
    @Override
    public void onEnable() {

        Instance = this;

        this.saveDefaultConfig();
        new Database(this);

        for (Listener l : listeners) {
            Bukkit.getPluginManager().registerEvents(l, this);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LevitationPotionCraft(), 0, 1);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SternLookCraft(), 0, 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new HandcuffsUpdater(), 0, 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Updater(), 0, 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SignParticlesSpawner(), 0, 50);

        if (Bukkit.getRecipe(NamespacedKey.fromString("handcuffs")) == null) Bukkit.addRecipe(new HandcuffsCraft());

        Bukkit.getPluginCommand("setsignurl").setExecutor(new OnSignCommand());
    }

    @Override
    public void onDisable() {

    }
}
