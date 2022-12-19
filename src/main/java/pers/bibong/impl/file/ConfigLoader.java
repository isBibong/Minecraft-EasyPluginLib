package pers.bibong.impl.file;

import pers.bibong.impl.EasyPluginLib;
import pers.bibong.lib.data.file.AbstractYamlLoader;
import pers.bibong.lib.data.file.AbstractYamlLoaderA;

public class ConfigLoader extends AbstractYamlLoader {
    private String prefix;

    public ConfigLoader () {
        super(EasyPluginLib.getInst(), "config.yml");
    }

    @Override
    public void load () {
        this.reload();

        this.prefix = this.getConfiguration().getString("Prefix");
    }

    public String getPrefix () {
        return prefix;
    }
}
