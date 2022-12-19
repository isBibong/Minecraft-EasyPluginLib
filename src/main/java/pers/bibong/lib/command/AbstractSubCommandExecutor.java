package pers.bibong.lib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractSubCommandExecutor implements CommandExecutor {
    private final String           description;
    private final List<Permission> permissions;

    public AbstractSubCommandExecutor (String description, List<Permission> permissions) {
        this.description = description;
        this.permissions = permissions;
    }

    @Override
    public boolean onCommand (@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                              @NotNull String[] strings) {
        return false;
    }

    public String getDescription () {
        return this.description;
    }

    public List<Permission> getPermissions () {
        return this.permissions;
    }

}
