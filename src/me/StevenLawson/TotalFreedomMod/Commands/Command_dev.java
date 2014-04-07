package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Developer Command Access", usage = "/<command> <Teston | Testoff>")
public class Command_dev extends TFM_Command
{
    
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1)
        {
            if (TFM_Util.DEVELOPERS.contains(sender.getName()))
            {
                if (args[0].equalsIgnoreCase("teston"))
                {
                    TFM_Util.adminAction("WARNING: " + sender.getName(), "Has Started Testing on this server.", true);
                    TFM_ConfigEntry.DEVELOPMENT_MODE.setBoolean(true);
                    for (Player player : server.getOnlinePlayers())
                    player.sendMessage(ChatColor.DARK_AQUA + "Warning: CJFreedom is currently in development mode. This means there may be unstable plugin builds on this server, and the server could crash more than normal!");
                    return true;
                }
            
                if (args[0].equalsIgnoreCase("testoff"))
                {
                    TFM_Util.adminAction("FINISHED: " + sender.getName(), "Has finished server side testing", true);
                    TFM_ConfigEntry.DEVELOPMENT_MODE.setBoolean(false);
                    return true;
                }
            }
            else 
            {
            sender.sendMessage(TotalFreedomMod.MSG_NO_PERMS);
            }
        }
        return false;
    }
    
}
