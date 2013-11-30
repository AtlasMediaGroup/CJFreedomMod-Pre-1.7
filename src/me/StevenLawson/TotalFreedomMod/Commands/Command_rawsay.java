package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.ONLY_CONSOLE, block_host_console = true)
@CommandParameters(description = "Broadcasts the given message. Supports colors.", usage = "/<command> <message>")
public class Command_rawsay extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length > 0)
        {
            if (args[0].equals("-a"))
            {
                for (Player player : server.getOnlinePlayers())
                {
                    if (TFM_SuperadminList.isUserSuperadmin(player))
                    {
                        TFM_Util.bcastMsg(TFM_Util.colorize(StringUtils.join(args, " ")));
                    }
                }
            }
            else
            {
                TFM_Util.bcastMsg(TFM_Util.colorize(StringUtils.join(args, " ")));
            }


        }

        return true;
    }
}
