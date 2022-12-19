package pers.bibong.impl.command;

import org.bukkit.plugin.java.JavaPlugin;
import pers.bibong.impl.command.sub.Reload;
import pers.bibong.impl.command.sub.Test;
import pers.bibong.lib.command.AbstractCommandExecutor;

public class EasyPluginLibCommandMain extends AbstractCommandExecutor {

    public EasyPluginLibCommandMain (JavaPlugin plugin) {
        super(plugin, "noPermission");
        this.register();

        this.putSubCommand("reload", new Reload());
        this.putSubCommand("test", new Test());
    }

}
