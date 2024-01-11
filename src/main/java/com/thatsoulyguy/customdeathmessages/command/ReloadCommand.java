package com.thatsoulyguy.customdeathmessages.command;

import com.thatsoulyguy.customdeathmessages.CustomDeathMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.security.cert.X509Extension;

public class ReloadCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(!sender.isOp())
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to access this command.");

        sender.sendMessage(ChatColor.YELLOW + "Attempting to Reload...");

        CustomDeathMessages.Instance.defaultConfig.Reload();

        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded!");

        return true;
    }
}