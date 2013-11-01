package me.StevenLawson.TotalFreedomMod.Commands;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Allows access to the remote panel control.", usage = "/<command> <reboot>")
public class Command_server extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        PanelMode mode = PanelMode.DONOTHING;
        sender.sendMessage(ChatColor.YELLOW + "Command accepted by server (Nothing should happen) - Standby...");

        if (args.length == 1)
        {
            if (args[0].equals("reboot"))
            {
                sender.sendMessage(ChatColor.YELLOW + "Command accepted by server (restart Accepted) - Standby...");
                mode = (PanelMode.UPDATE);
            }
        }

        if (args.length == 2)
        {
            return false;

        }

        return true;
    }
    public static void updateLogsRegistration(final CommandSender sender, final Player target, final Command_server.PanelMode mode)
    {
        updateLogsRegistration(sender, target.getName(), target.getAddress().getAddress().getHostAddress().trim(), mode);
    }

    public static void updateLogsRegistration(final CommandSender sender, final String targetName, final String targetIP, final PanelMode mode)
    {
        final String PanelURL = TFM_ConfigEntry.PANEL_URL.getString() + "?apikey=";
        final String PanelAPI = TFM_ConfigEntry.PANEL_API_KEY.getString();

        if (PanelURL == null || PanelAPI == null || PanelURL.isEmpty() || PanelAPI.isEmpty())
        {
            return;
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (sender != null)
                    {
                        sender.sendMessage(ChatColor.YELLOW + "Connecting to the panel API - Standby...");
                         TFM_Util.adminAction(sender.getName(), "is Connecting to the panel's API - Please Standby", true);
                    }

                    URL url = new URLBuilder(PanelURL)
                            .addQueryParameter("api", PanelAPI)
                            .addQueryParameter("&action" + "mode", mode.toString())
                           // .addQueryParameter("name", targetName)
                            .getURL();

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(1000 * 5);
                    connection.setReadTimeout(1000 * 5);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("HEAD");

                    final int responseCode = connection.getResponseCode();

                    if (sender != null)
                    {
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                if (responseCode == 200)
                                {
                                    sender.sendMessage(ChatColor.GREEN + "Registration " + mode.toString() + "d.");
                                }
                                else
                                {
                                    sender.sendMessage(ChatColor.RED + "Error contacting logs registration server.");
                                }
                            }
                        }.runTask(TotalFreedomMod.plugin);
                    }
                }
                catch (Exception ex)
                {
                    TFM_Log.severe(ex);
                }
            }
        }.runTaskAsynchronously(TotalFreedomMod.plugin);
    }

    public static enum PanelMode
    {
        UPDATE("restart"), DONOTHING("");
        private final String mode;

        private PanelMode(String mode)
        {
            this.mode = mode;
        }

        @Override
        public String toString()
        {
            return mode;
        }
    }

    private static class URLBuilder
    {
        private final String requestPath;
        private final Map<String, String> queryStringMap = new HashMap<String, String>();

        public URLBuilder(String requestPath)
        {
            this.requestPath = requestPath;
        }

        public URLBuilder addQueryParameter(String key, String value)
        {
            queryStringMap.put(key, value);
            return this;
        }

        public URL getURL() throws MalformedURLException
        {
            List<String> pairs = new ArrayList<String>();
            Iterator<Entry<String, String>> it = queryStringMap.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<String, String> pair = it.next();
                pairs.add(pair.getKey() + "=" + pair.getValue());
            }

            return new URL(requestPath + "" + StringUtils.join(pairs, ""));
        }
    }
}
