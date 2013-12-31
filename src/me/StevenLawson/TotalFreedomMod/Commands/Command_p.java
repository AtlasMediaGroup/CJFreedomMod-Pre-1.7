package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, dlevel = DonatorLevel.STANDARD, source = SourceType.BOTH)
@CommandParameters(
        description = "DonatorChat - Talk privately with other donators! Using <command> itself will toggle DonatorChat on and off for all messages.",
        usage = "/<command> [message...]",
        aliases = "donatorchat")
public class Command_p extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                playerMsg("Only in-game players can toggle DonatorChat.");
                return true;
            }

            TFM_PlayerData userinfo = TFM_PlayerData.getPlayerData(sender_p);
            userinfo.setDonatorChat(!userinfo.inDonatorChat());
            playerMsg("Toggled Donator Chat " + (userinfo.inAdminChat() ? "on" : "off") + ".");
        }
        else
        {
            TFM_Util.donatorChatMessage(sender, StringUtils.join(args, " "), senderIsConsole);
        }

        return true;
    }
}
