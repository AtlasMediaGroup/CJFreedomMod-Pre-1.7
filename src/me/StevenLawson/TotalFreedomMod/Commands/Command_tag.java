package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Arrays;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Sets yourself a prefix", usage = "/<command> <<prefix> | off | clear <player> | clearall>")
public class Command_tag extends TFM_Command
{
    public static final List<String> FORBIDDEN_WORDS = Arrays.asList(new String[]
    {
        "admin", "owner", "moderator", "developer", "&k"
    });

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1)
        {
            if ("clearall".equals(args[0]))
            {
                if (!TFM_SuperadminList.isUserSuperadmin(sender))
                {
                    playerMsg(TotalFreedomMod.MSG_NO_PERMS);
                    return true;
                }

                TFM_Util.adminAction(sender.getName(), "Removing all tags", false);
                int count = 0;
                for (Player p : server.getOnlinePlayers())
                {
                    TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);

                    if (playerdata.getTag() != null)
                    {
                        count++;
                        TFM_PlayerData.getPlayerData(p).setTag(null);
                    }
                }

                playerMsg(count + " tag(s) removed.");
                return true;
            }

            if (senderIsConsole)
            {
                playerMsg("Only in-game players can set tags. Use \"/tag clearall\" to reset all tags.");
                return true;
            }

            if (TFM_Util.isStopCommand(args[0]))
            {
                TFM_PlayerData.getPlayerData(sender_p).setTag(null);
                playerMsg("Your tag has been removed.");
                return true;
            }

            if (args[0].length() > 15)
            {
                playerMsg("That tag is too long.");
                return true;
            }

            if (!TFM_SuperadminList.isUserSuperadmin(sender))
            {
                for (String word : FORBIDDEN_WORDS)
                {
                    if (args[0].toLowerCase().contains(word.toLowerCase()))
                    {
                        if (word.contains(String.valueOf(ChatColor.COLOR_CHAR)))
                        {
                            playerMsg("That tag contains a forbidden color-code.");
                        }
                        else
                        {
                            playerMsg("That tag contains a forbidden word.");
                        }
                        return true;
                    }
                }

            }

            TFM_PlayerData.getPlayerData(sender_p).setTag(args[0]);
            playerMsg("Tag set.");

            return true;
        }
        else if (args.length == 2)
        {
            if ("clear".equals(args[0]))
            {
                if (!TFM_SuperadminList.isUserSuperadmin(sender))
                {
                    playerMsg(TotalFreedomMod.MSG_NO_PERMS);
                    return true;
                }

                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage());
                    return true;
                }

                TFM_PlayerData.getPlayerData(p).setTag(null);
                playerMsg("Removed " + p.getName() + "'s tag.");
                return true;
            }

            return false;
        }

        return false;
    }
}
