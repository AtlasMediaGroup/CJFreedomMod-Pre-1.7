package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Toggle the nighttime.", usage = "/<command>")
public class Command_toggletime extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length != 0)
        {
            return false;
        }
        
        String mode;
        
        if (TFM_ConfigEntry.DISABLE_NIGHT.getBoolean() == true)
        {
            TFM_ConfigEntry.DISABLE_NIGHT.setBoolean(false);
            mode = "off";
        }
        
        else
        {
            TFM_ConfigEntry.DISABLE_NIGHT.setBoolean(true);
            mode = "on";
        }
        
        TFM_Util.adminAction(sender.getName(), "Toggling night " + mode, true);
        return true;
    }
}
