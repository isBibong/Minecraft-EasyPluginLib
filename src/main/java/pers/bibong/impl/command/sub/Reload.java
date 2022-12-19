package pers.bibong.impl.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.bibong.impl.EasyPluginLib;
import pers.bibong.lib.command.AbstractSubCommandExecutor;
import pers.bibong.lib.message.MessageManager;

import java.util.ArrayList;

public class Reload extends AbstractSubCommandExecutor
{

    public Reload ()
    {
        super("重新加載插件設定。", new ArrayList<>());
    }

    @Override
    public boolean onCommand (
            @NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings
    )
    {

        EasyPluginLib.getInst().reload();
        MessageManager.sendColorMessageToCommandSender(commandSender, null, EasyPluginLib.getInst().config.getPrefix() + " &a重讀插件設定成功。");
        return true;
    }

}
