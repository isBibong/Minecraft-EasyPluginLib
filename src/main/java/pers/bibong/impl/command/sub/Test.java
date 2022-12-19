package pers.bibong.impl.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.bibong.impl.file.PlayersLoader;
import pers.bibong.lib.command.AbstractSubCommandExecutor;
import pers.bibong.lib.message.MessageManager;

import java.util.ArrayList;

public class Test extends AbstractSubCommandExecutor {

    public Test () {
        super("測試讀Yaml", new ArrayList<>());
    }

    @Override
    public boolean onCommand (
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    )
    {

        if (!(commandSender instanceof Player player)) {
            return false;
        }

        PlayersLoader playersLoader = new PlayersLoader(player.getUniqueId().toString());
        String        string        = playersLoader.getConfiguration().getString("Setting.TestValue");
        MessageManager.sendColorMessageToCommandSender(player, null, string);
        return true;
    }
}
