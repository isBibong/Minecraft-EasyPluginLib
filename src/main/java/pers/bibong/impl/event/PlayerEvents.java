package pers.bibong.impl.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import pers.bibong.impl.file.PlayersLoader;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoinEvent (@NotNull PlayerJoinEvent event) {
        Player        player        = event.getPlayer();
        PlayersLoader playersLoader = new PlayersLoader(player.getUniqueId().toString());
        playersLoader.getConfiguration().set("Setting.TestValue", 0);
        playersLoader.save();
    }

}
