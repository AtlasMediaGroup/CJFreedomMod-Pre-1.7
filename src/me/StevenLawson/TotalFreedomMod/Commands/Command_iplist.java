package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.*;
import org.bukkit.ChatColor;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "List all online players current IP's.", usage = "/<command>", aliases = "opil")
public class Command_iplist extends TFM_Command
{
	@Override
    public boolean run(CommandSender sender, Player p, Command cmd, String lbl, String[] args, boolean senderIsConsole)
    {
        playerMsg(getPlayerIPs());
        
        return true;
    }
    
    private String getPlayerIPs()
    {
        StringBuilder sb = new StringBuilder();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            String IP = player.getAddress().getAddress().getHostAddress();
            String name = player.getName() + " - " + IP;
            
            if (name.length() > 0)
            {
                sb.append(ChatColor.WHITE);
                sb.append(", ");
            }
            
            sb.append(ChatColor.RED);
            sb.append(name);
        }
        
        return "(Players + IPs): " + sb.toString();
    }
}