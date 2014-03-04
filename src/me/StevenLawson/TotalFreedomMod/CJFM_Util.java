package me.StevenLawson.TotalFreedomMod;

import java.util.Arrays;
import java.util.List;
import me.RyanWild.CJFreedomMod.CJFM_DonatorList;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.getPrefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;





public class CJFM_Util
{
    
    
    public static final List<String> EXECUTIVES = Arrays.asList("Camzie99", "Phoenix411", "Kyled1986", "andoodle");
    public static final List<String> SYSADMINS = Arrays.asList("wild1145", "Varuct", "thecjgcjg", "DarthSalamon");
    
    
    
    

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
    
    
    
    
    
    
    
    
}
