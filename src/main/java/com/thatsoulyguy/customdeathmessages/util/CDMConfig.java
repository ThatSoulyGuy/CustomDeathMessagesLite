package com.thatsoulyguy.customdeathmessages.util;

import com.thatsoulyguy.customdeathmessages.CustomDeathMessages;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CDMConfig
{
    private File configFile;
    private FileConfiguration config;
    private String path;

    public void Initialize(String path)
    {
        this.path = path;

        configFile = new File(CustomDeathMessages.Instance.getDataFolder(), path);

        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            CustomDeathMessages.Instance.saveResource(path, false);
        }

        config = new YamlConfiguration();

        try
        {
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public void Reload()
    {
        configFile = new File(CustomDeathMessages.Instance.getDataFolder(), path);

        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            CustomDeathMessages.Instance.saveResource(path, false);
        }

        config = new YamlConfiguration();

        try
        {
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public void SetValue(String path, Object value)
    {
        config.set(path, value);
        Save();
    }

    public Object GetValue(String path)
    {
        return config.get(path);
    }

    public void Save()
    {
        try
        {
            config.save(configFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}