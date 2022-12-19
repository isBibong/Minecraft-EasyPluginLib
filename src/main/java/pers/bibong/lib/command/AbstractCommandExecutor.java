package pers.bibong.lib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.bibong.lib.message.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommandExecutor implements TabExecutor
{
    protected final JavaPlugin plugin;
    protected final String     noPermissionMessage;

    public AbstractCommandExecutor (
            JavaPlugin plugin, String noPermissionMessage
    )
    {
        this.plugin              = plugin;
        this.noPermissionMessage = noPermissionMessage;
    }

    @Override
    public boolean onCommand (
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String @NotNull [] strings
    )
    {

        Map<String, AbstractSubCommandExecutor> subCommands = CommandManager.getSubCommands(this.plugin);

        if (strings.length < 1)
        {
            final StringBuilder help = new StringBuilder();
            subCommands.forEach((n, cmd) -> help.append(n).append(": ").append(cmd.getDescription()).append("\n"));

            help.delete(help.length() - 1, help.length());
            MessageManager.sendColorMessageToCommandSender(commandSender, null, help.toString());
            return true;
        }

        if (!subCommands.containsKey(strings[0]))
        {
            return false;
        }

        AbstractSubCommandExecutor subCommand      = subCommands.get(strings[0]);
        List<Permission>           permissions     = subCommand.getPermissions();
        boolean                    checkPermission = CommandManager.checkPermission(commandSender, permissions);
        if (!checkPermission)
        {
            MessageManager.sendColorMessageToCommandSender(commandSender, null, this.noPermissionMessage);
            return false;
        }

        return subCommand.onCommand(commandSender, command, s, strings);
    }

    @Nullable
    @Override
    public List<String> onTabComplete (
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String @NotNull [] strings
    )
    {

        List<String> subCommands = new ArrayList<>();

        if (strings.length == 1)
        {
            CommandManager.getSubCommands(this.plugin).forEach((cmd, commandExecutor) -> {
                List<Permission> permissions = commandExecutor.getPermissions();
                permissions.forEach(permission -> {
                    if (!commandSender.hasPermission(permission))
                    {
                        return;
                    }

                    subCommands.add(cmd);
                });
            });
        }

        return subCommands;
    }

    protected void register ()
    {
        CommandManager.register(this.plugin);
    }

    protected void putSubCommand (
            String subCommand, AbstractSubCommandExecutor subCommandExecutor
    )
    {
        CommandManager.putSubCommand(this.plugin, subCommand, subCommandExecutor);
    }
}
