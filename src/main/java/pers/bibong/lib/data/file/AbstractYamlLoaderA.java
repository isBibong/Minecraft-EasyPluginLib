package pers.bibong.lib.data.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public abstract class AbstractYamlLoaderA
{
    protected final JavaPlugin plugin;
    protected final String     fileName;

    protected final File              file;
    protected       YamlConfiguration configuration;

    public AbstractYamlLoaderA (final @NotNull JavaPlugin plugin, final String fileName)
    {
        this.plugin   = plugin;
        this.fileName = fileName;
        this.file     = new File(plugin.getDataFolder(), this.fileName);

        if (!this.file.exists())
        {
            this.file.mkdirs();

            this.plugin.saveResource(this.fileName, false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public AbstractYamlLoaderA (final @NotNull JavaPlugin plugin, final String path, final String fileName)
    {
        this.plugin   = plugin;
        this.fileName = fileName;
        this.file     = new File(plugin.getDataFolder(), path + fileName);

        final File folder = new File(plugin.getDataFolder(), path);

        try
        {
            if (!folder.exists())
            {
                folder.mkdirs();
            }
            this.file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public abstract void load ();

    protected void reload ()
    {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save ()
    {
        try
        {
            this.configuration.save(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected Plugin getPlugin ()
    {
        return this.plugin;
    }

    protected String getFileName ()
    {
        return this.fileName;
    }

    public File getFile ()
    {
        return this.file;
    }

    public YamlConfiguration getConfiguration ()
    {
        return this.configuration;
    }
}
