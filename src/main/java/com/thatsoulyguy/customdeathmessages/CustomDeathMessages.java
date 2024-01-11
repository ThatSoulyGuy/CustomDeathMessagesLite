package com.thatsoulyguy.customdeathmessages;

import com.thatsoulyguy.customdeathmessages.command.ReloadCommand;
import com.thatsoulyguy.customdeathmessages.event.PlayerEvents;
import com.thatsoulyguy.customdeathmessages.util.CDMConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CustomDeathMessages extends JavaPlugin
{
    public CDMConfig defaultConfig = new CDMConfig();

    public static CustomDeathMessages Instance = null;

    public static String TranslateConfigMessage(String input, Player victim, Player killer, int killcount)
    {
        String out = String.copyValueOf(input.toCharArray());
        
        out = out.replace("%VICTIM%", victim.getName());
        
        if(killer != null)
            out = out.replace("%KILLER%", killer.getName());

        if(killcount != -1)
            out = out.replace("%KILL_COUNT%", String.valueOf(killcount));
        
        return out;
    }
    
    public static String GetPlayerPlaceholder(Player player, String placeholder)
    {
        return PlaceholderAPI.setPlaceholders(player, placeholder);
    }

    public static void BroadcastToAllPlayers(String... messages)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(messages);
    }

    public static String FormatChatColor(String input)
    {
        return input.replace('&', ChatColor.COLOR_CHAR);
    }

    @Override
    public void onEnable()
    {
        Instance = this;

        Objects.requireNonNull(Bukkit.getWorld("world")).setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);

        Objects.requireNonNull(getCommand("cdmreload")).setExecutor(new ReloadCommand());
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        defaultConfig.Initialize("config.yml");
    }

    @Override
    public void onDisable()
    {

    }
}
