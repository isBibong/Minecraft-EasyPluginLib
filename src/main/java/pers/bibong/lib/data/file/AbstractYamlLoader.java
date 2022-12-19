package pers.bibong.lib.data.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractYamlLoader
{
    private final JavaPlugin        plugin;
    private final String            fileName;
    private final Path              filePath;
    private final File              file;
    private       YamlConfiguration configuration;

    public AbstractYamlLoader (
            @NotNull JavaPlugin plugin, String fileName
    )
    {
        this(plugin, "", fileName, false);
    }

    public AbstractYamlLoader (
            @NotNull JavaPlugin plugin, String path, String fileName, boolean createFolder
    )
    {
        this.plugin   = plugin;
        this.fileName = fileName;
        this.filePath = plugin.getDataFolder().toPath().resolve(path).resolve(fileName);

        if (createFolder)
        {
            try
            {
                Files.createDirectories(this.filePath.getParent());
            }
            catch (IOException e)
            {
                plugin.getLogger().warning("Failed to create directories for file: " + this.filePath);
            }
        }

        if (!Files.exists(this.filePath))
        {
            plugin.saveResource(fileName, false);
        }

        this.file          = this.filePath.toFile();
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
            this.configuration.save(this.file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public JavaPlugin getPlugin ()
    {
        return plugin;
    }

    public String getFileName ()
    {
        return fileName;
    }

    public Path getFilePath ()
    {
        return filePath;
    }

    public YamlConfiguration getConfiguration ()
    {
        return configuration;
    }
}