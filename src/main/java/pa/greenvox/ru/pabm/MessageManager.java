package pa.greenvox.ru.pabm;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    public static void sendMessage(CommandSender sender, Message message) {

        String msg = "";

        switch (message) {
            case BlockIsntTargeted:
                msg = PABM.Instance.getConfig().getString("block_isnt_targeted");
                break;
            case YouHaventUrl:
                msg = PABM.Instance.getConfig().getString("you_havent_url");
                break;
            case TargetedBlockIsntSign:
                msg = PABM.Instance.getConfig().getString("targeted_block_isnt_sign");
                break;
        }

        if (msg == null) return;

        msg = ChatColor.translateAlternateColorCodes('&', msg);

        sender.sendMessage(msg);
    }
}

