package pers.bibong.impl.file;

import org.jetbrains.annotations.NotNull;
import pers.bibong.impl.EasyPluginLib;
import pers.bibong.lib.data.file.AbstractYamlLoader;

import java.io.File;

public class PlayersLoader extends AbstractYamlLoader {

    public PlayersLoader (@NotNull String playerUUID) {
        super(EasyPluginLib.getInst(), File.separator + "players" + File.separator, playerUUID + ".yml", true);
    }

    @Override
    public void load () {
        this.reload();
    }

}
