package pers.bibong.lib.message;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageManager
{

    public static void sendColorMessageToCommandSender (
            final @NotNull CommandSender commandSender, @Nullable String identifier, final @NotNull String message
    )
    {

        identifier = identifier == null ? "&" : identifier;
        commandSender.sendMessage(MessageManager.toColorMessage(identifier, message));
    }

    public static void sendColorMessageToConsole (
            final @NotNull ConsoleCommandSender consoleCommandSender,
            @Nullable String identifier,
            final @NotNull String message
    )
    {

        identifier = identifier == null ? "&" : identifier;
        consoleCommandSender.sendMessage(toColorMessage(identifier, message));
    }

    public static void sendWarningMessageToConsole (
            @NotNull JavaPlugin plugin, @NotNull String message, Throwable throwable
    )
    {

        plugin.getLogger().warning(message + "\n" + throwable);
    }

    public static @NotNull String toColorMessage (final @NotNull String message)
    {
        return MessageManager.toColorMessage("&", message);
    }

    public static @NotNull String toColorMessage (
            final @NotNull String identifier, final @NotNull String message
    )
    {
        String msg = MessageManager.toRGB(message);

        if (msg.contains(identifier))
        {
            msg = ChatColor.translateAlternateColorCodes(identifier.charAt(0), msg);
        }

        return msg;
    }

    public static String toRGB (final @NotNull String message)
    {
        return Pattern.compile("<#([A-Fa-f0-9]){6}>")
                .matcher(message)
                .results()
                .map(result -> result.group().substring(1, result.group().length() - 1))
                .map(net.md_5.bungee.api.ChatColor::of)
                .reduce(message, (acc, hexColor) -> acc.replaceAll(hexColor.toString(), hexColor + "$1"), String::concat);
    }

    public static String toRGBA (final @NotNull String message)
    {
        String msg = message;

        final Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");
        Matcher       matcher    = hexPattern.matcher(msg);

        while (matcher.find())
        {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String    before   = msg.substring(0, matcher.start());
            final String    after    = msg.substring(matcher.end());
            msg     = before + hexColor + after;
            matcher = hexPattern.matcher(msg);
        }

        return msg;
    }
}
