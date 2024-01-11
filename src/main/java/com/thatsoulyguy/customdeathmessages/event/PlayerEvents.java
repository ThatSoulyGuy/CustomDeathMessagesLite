package com.thatsoulyguy.customdeathmessages.event;

import com.thatsoulyguy.customdeathmessages.CustomDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerEvents implements Listener
{

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event)
    {
        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();

        if(killer != null)
        {
            int kills = Integer.parseInt(CustomDeathMessages.GetPlayerPlaceholder(killer, "%statistic_player_kills%"));

            if(kills > 99)
                CustomDeathMessages.BroadcastToAllPlayers(CustomDeathMessages.FormatChatColor(CustomDeathMessages.TranslateConfigMessage((String)CustomDeathMessages.Instance.defaultConfig.GetValue("messages.player-killed-by-player-over-99"), deceased, killer, kills)));
            else
                CustomDeathMessages.BroadcastToAllPlayers(CustomDeathMessages.FormatChatColor(CustomDeathMessages.TranslateConfigMessage((String)CustomDeathMessages.Instance.defaultConfig.GetValue("messages.player-killed-by-player"), deceased, killer, -1)));
        }
        else
        {
            EntityDamageEvent lastDamageCause = deceased.getLastDamageCause();

            if (lastDamageCause instanceof EntityDamageByEntityEvent)
            {
                EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) lastDamageCause;
                Entity damager = damageEvent.getDamager();

                if (!(damager instanceof Player))
                    CustomDeathMessages.BroadcastToAllPlayers(CustomDeathMessages.FormatChatColor(CustomDeathMessages.TranslateConfigMessage((String)CustomDeathMessages.Instance.defaultConfig.GetValue("messages.player-killed-by-mob"), deceased, null, -1)));
            }
            else if (lastDamageCause != null && lastDamageCause.getCause() == EntityDamageEvent.DamageCause.FALL)
                CustomDeathMessages.BroadcastToAllPlayers(CustomDeathMessages.FormatChatColor(CustomDeathMessages.TranslateConfigMessage((String)CustomDeathMessages.Instance.defaultConfig.GetValue("messages.player-killed-by-fall"), deceased, null, -1)));
            else
                CustomDeathMessages.BroadcastToAllPlayers(CustomDeathMessages.FormatChatColor(CustomDeathMessages.TranslateConfigMessage((String)CustomDeathMessages.Instance.defaultConfig.GetValue("messages.player-killed-by-unknown"), deceased, null, -1)));
        }
    }

    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage();
        boolean foundPlayer = false;

        String[] words = message.split(" ");

        for (String word : words)
        {
            if (word.startsWith("@"))
            {
                String playerName = word.substring(1);
                Player mentionedPlayer = Bukkit.getPlayerExact(playerName);

                if (mentionedPlayer != null && mentionedPlayer.isOnline())
                {
                    mentionedPlayer.playSound(mentionedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);

                    message = message.replace(word, "§e" + word + "§r");
                    foundPlayer = true;
                }
            }
        }

        if (foundPlayer)
            event.setMessage(message);
    }
}