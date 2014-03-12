package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows you how to become a admin.", usage = "/<command>", aliases = "bi")
public class Command_baninfo extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if(args.length != 0)
        {
            return false;
        }
        else
        {
            playerMsg(ChatColor.AQUA + "The following is accurate as of 11/03/2014");
            playerMsg(ChatColor.AQUA + "To appeal a ban, you need to go to the forums at http://www.thecjgcjg.com/forums");
            playerMsg(ChatColor.AQUA + "Go to the 'Ban Appeals' board.");
            playerMsg(ChatColor.AQUA + "Read the template and make a new thread.");
            playerMsg(ChatColor.AQUA + "Fill out the answers to the template (TRUTHFULLY).");
            playerMsg(ChatColor.RED + "DO NOT Lie on your appeal, this is a way just to get it rejected!");
            playerMsg(ChatColor.AQUA + "Good Luck!");
            return true;
        }
    }
}
