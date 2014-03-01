//Made by Hawkeyeshi
package me.StevenLawson.TotalFreedomMod.Commands;

import static me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.isSuperadminImpostor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows you the CJFreedom Admin Banning Regulations!", usage = "/<command>")
public class Command_varuct extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        
        Player varuct = Bukkit.getServer().getPlayer("Varuct");
        
        if(varuct.isOnline() && !isSuperadminImpostor(varuct))
        {
            playerMsg(ChatColor.GREEN + "The owner is " + ChatColor.BLUE + "online" + ChatColor.GREEN + "!");
           
        }else if(!varuct.isOnline() || isSuperadminImpostor(varuct))
        {
            playerMsg(ChatColor.GREEN + "The owner is " + ChatColor.DARK_RED + "offline" + ChatColor.GREEN + "!");
        }    
        
        return false;
    }
}