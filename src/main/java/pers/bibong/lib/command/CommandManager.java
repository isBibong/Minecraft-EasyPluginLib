package pers.bibong.lib.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandManager
{
    private final static Map<JavaPlugin, Map<String, AbstractSubCommandExecutor>> pluginSubCommandsManager = new HashMap<>();

    public static void register (JavaPlugin plugin)
    {
        CommandManager.pluginSubCommandsManager.put(plugin, new HashMap<>());
    }

    public static void putSubCommand (
            JavaPlugin plugin, String subCommand, AbstractSubCommandExecutor subCommandExecutor
    )
    {
        Map<String, AbstractSubCommandExecutor> subCommands = CommandManager.getSubCommands(plugin);
        subCommands.put(subCommand, subCommandExecutor);
    }

    public static boolean checkPermission (CommandSender commandSender, @NotNull List<Permission> permissions)
    {
        for (Permission permission : permissions)
        {
            if (commandSender.hasPermission(permission))
            {
                return true;
            }
        }
        return false;
    }

    public static Map<String, AbstractSubCommandExecutor> getSubCommands (JavaPlugin plugin)
    {
        return CommandManager.pluginSubCommandsManager.get(plugin);
    }

    public static Map<JavaPlugin, Map<String, AbstractSubCommandExecutor>> getSubCommandsManager ()
    {
        return pluginSubCommandsManager;
    }
}
