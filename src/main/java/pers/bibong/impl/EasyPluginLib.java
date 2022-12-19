package pers.bibong.impl;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pers.bibong.impl.command.EasyPluginLibCommandMain;
import pers.bibong.impl.event.PlayerEvents;
import pers.bibong.impl.file.ConfigLoader;

public final class EasyPluginLib extends JavaPlugin {
    private static EasyPluginLib inst;

    public ConfigLoader config;

    @Override
    public void onLoad () {
        EasyPluginLib.inst = this;
    }

    @Override
    public void onEnable () {
        this.regCommandMain();
        this.regEvents();
        this.loadYaml();
    }

    public void reload () {
        this.config.load();
    }

    public void loadYaml () {
        this.config = new ConfigLoader();
    }

    private void regEvents () {
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    public void regCommandMain () {
        PluginCommand easypluginlib = getCommand("easypluginlib");
        if (easypluginlib == null) { throw new NullPointerException("can't find command [easypluginlib]."); }
        easypluginlib.setExecutor(new EasyPluginLibCommandMain(this));
    }

    public static EasyPluginLib getInst () {
        return inst;
    }
}
