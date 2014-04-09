package me.RyanWild.CJFreedomMod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.getPrefix;
import static me.StevenLawson.TotalFreedomMod.TotalFreedomMod.mySQL;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CJFM_Util
{

    public static final List<String> EXECUTIVES = Arrays.asList("camzie99", "phoenix411", "kyled1986", "andoodle");
    public static final List<String> SYSADMINS = Arrays.asList("wild1145", "varuct", "thecjgcjg", "darthsalamon");
    public static final List<String> DEVELOPERS = Arrays.asList("Madgeek1450", "DarthSalamon", "wild1145", "Paldiu", "MrPorkSausage", "Camzie99");

    public static void donatorChatMessage(CommandSender sender, String message, boolean senderIsConsole)
    {
        String name = sender.getName() + " " + getPrefix(sender, senderIsConsole);
        TFM_Log.info("[DONATOR] " + name + ": " + message);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (TFM_SuperadminList.isUserSuperadmin(player) || (CJFM_DonatorList.isUserDonator(player)))
            {
                player.sendMessage("[" + ChatColor.DARK_GREEN + "DONATOR" + ChatColor.WHITE + "] " + ChatColor.GOLD + name + ": " + ChatColor.DARK_GREEN + message);
            }
        }
    }

    private CoreProtectAPI getCoreProtect()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (plugin == null || !(plugin instanceof CoreProtect))
        {
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.APIVersion() < 2)
        {
            return null;
        }
        return CoreProtect;
    }

    public static void updateDatabase(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate(SQLquery);
    }

    public void getValueFromDB(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        ResultSet res = statement.executeQuery(SQLquery);
        res.next();
    }

    public static void loadDonatorConfig()
    {
        try
        {
            CJFM_DonatorList.backupSavedList();
            CJFM_DonatorList.loadDonatorList();
        }
        catch (Exception ex)
        {
            TFM_Log.severe("Error loading donator list: " + ex.getMessage());
        }
    }

}
