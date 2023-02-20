package pa.greenvox.ru.pabm.signs;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pa.greenvox.ru.pabm.Message;
import pa.greenvox.ru.pabm.MessageManager;
import pa.greenvox.ru.pabm.database.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pa.greenvox.ru.pabm.database.Database.getBlockKey;

public class OnSignCommand implements CommandExecutor {

    private String sumArgs(String[] args) {
        return String.join(" ", args);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        Block b = p.getTargetBlock(5);
        if (b == null) {
            MessageManager.sendMessage(sender, Message.BlockIsntTargeted);
            return true;
        }
        if (!Tag.SIGNS.isTagged(b.getType())) {
            MessageManager.sendMessage(sender, Message.TargetedBlockIsntSign);
            return true;
        }
        if (args.length == 0) {
            MessageManager.sendMessage(sender, Message.YouHaventUrl);
            return true;
        }

        String url = sumArgs(args);
        FileConfiguration cfg = Database.Instance.getConfig();

        cfg.set("signs." + getBlockKey(b), url);

        if (!cfg.contains("signs_list")) cfg.set("signs_list", Arrays.asList(getBlockKey(b)));
        else {
            List<String> signs = cfg.getStringList("signs_list");
            if (!signs.contains(getBlockKey(b))) {
                signs.add(getBlockKey(b));

                cfg.set("signs_list", signs);
            }
        }

        Database.Instance.saveConfig(cfg);

        return true;
    }
}
