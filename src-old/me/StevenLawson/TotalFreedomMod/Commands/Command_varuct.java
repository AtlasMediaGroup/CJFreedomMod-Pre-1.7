//Made by Hawkeyeshi
package me.StevenLawson.TotalFreedomMod.Commands;

import static me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.isSuperadminImpostor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Check whether or not Varuct (The Owner) is online!", usage = "/<command>", aliases = "owner")
public class Command_varuct extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        
        Player varuct = Bukkit.getServer().getPlayer("Varuct");
        
        if(varuct != null && !isSuperadminImpostor(varuct))
        {
            playerMsg(ChatColor.GREEN + "The owner is " + ChatColor.BLUE + "online" + ChatColor.GREEN + "!");
            return true;
           
        }else if(varuct == null || isSuperadminImpostor(varuct))
        {
            playerMsg(ChatColor.GREEN + "The owner is " + ChatColor.DARK_RED + "offline" + ChatColor.GREEN + "!");
            return true;
        }    
        
        
        else
        {
            
        }
        
        return false;
    }
}