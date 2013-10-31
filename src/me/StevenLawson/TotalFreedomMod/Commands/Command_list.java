package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.ArrayList;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.RyanWild.CJFreedomMod.CJFM_DonatorList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Lists the real names of all online players.", usage = "/<command>", aliases = "who")
public class Command_list extends TFM_Command
{
    private static enum ListFilter
    {
        SHOW_ALL, SHOW_ADMINS, SHOW_DONATORS
    }

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (TFM_Util.isFromHostConsole(sender.getName()))
        {
            List<String> player_names = new ArrayList<String>();
            for (Player player : server.getOnlinePlayers())
            {
                player_names.add(player.getName());
            }
            playerMsg("There are " + player_names.size() + "/" + server.getMaxPlayers() + " players online:\n" + StringUtils.join(player_names, ", "), ChatColor.WHITE);
            return true;
        }

        ListFilter listFilter = ListFilter.SHOW_ALL;
        if (args.length >= 1)
        {
            if (args[0].equalsIgnoreCase("-a"))
            {
                listFilter = ListFilter.SHOW_ADMINS;
            }

            if (args[0].equalsIgnoreCase("-d"))
            {
                listFilter = ListFilter.SHOW_DONATORS;
            }
        }

        StringBuilder onlineStats = new StringBuilder();
        StringBuilder onlineUsers = new StringBuilder();

        onlineStats.append(ChatColor.BLUE).append("There are currently ").append(ChatColor.RED).append(server.getOnlinePlayers().length);
        onlineStats.append(ChatColor.BLUE).append(" out of a maximum ").append(ChatColor.RED).append(server.getMaxPlayers());
        onlineStats.append(ChatColor.BLUE).append(" players online.");

        List<String> player_names = new ArrayList<String>();
        for (Player player : server.getOnlinePlayers())
        {
            boolean userSuperadmin = TFM_SuperadminList.isUserSuperadmin(player);

            if (listFilter == ListFilter.SHOW_ADMINS && !userSuperadmin)
            {
                continue;
            }

            String prefix = "";


            if (userSuperadmin)
            {
                if (TFM_SuperadminList.isSeniorAdmin(player))
                {
                    prefix = (ChatColor.LIGHT_PURPLE + "[SrA]");
                }
                else
                {
                    prefix = (ChatColor.AQUA + "[SA]");
                }

                if (TFM_Util.DEVELOPERS.contains(player.getName()))
                {
                    prefix = (ChatColor.DARK_PURPLE + "[Dev]");
                }

                if (player.getName().equalsIgnoreCase("wild1145"))
                {
                    prefix = (ChatColor.DARK_GREEN + "[Chief Developer & System Admin]");
                }

                if (player.getName().equalsIgnoreCase("thecjgcjg"))
                {
                    prefix = (ChatColor.DARK_PURPLE + "[Retired Owner & System Admin]");
                }
                if (player.getName().equalsIgnoreCase("DarthSalamon"))
                {
                    prefix = (ChatColor.DARK_PURPLE + "[System Admin & TFM Guru]");
                }

                if (player.getName().equalsIgnoreCase("Varuct"))
                {
                    prefix = (ChatColor.DARK_PURPLE + "[Owner & System Admin]");
                }

                if (player.getName().equalsIgnoreCase("markbyron"))
                {
                    prefix = (ChatColor.GREEN + "[TotalFreedom Owner]");
                }

                if (player.getName().equalsIgnoreCase("phoenix411"))
                {
                    prefix = (ChatColor.DARK_AQUA + "[Chief Of Security]");
                }

                if (player.getName().equalsIgnoreCase("lynxlps"))
                {
                    prefix = (ChatColor.DARK_AQUA + "[Executive Admin Manager]");
                }

            }
            else
            {
                if (player.isOp())
                {
                    prefix = (ChatColor.RED + "[OP]");
                }
            }

            boolean userDonator = CJFM_DonatorList.isUserDonator(player);

            if (listFilter == ListFilter.SHOW_DONATORS && !userDonator)
            {
                continue;
            }

            if (userDonator)
            {
                if (CJFM_DonatorList.isSeniorDonator(player))
                {
                    prefix = (ChatColor.LIGHT_PURPLE + "[Senior-Donator]");
                }
                else
                {
                    prefix = (ChatColor.DARK_AQUA + "[Donator]");
                }

            }

            boolean usersradminDonator = CJFM_DonatorList.isUserDonator(player) && TFM_SuperadminList.isUserSuperadmin(player);

            if (listFilter == ListFilter.SHOW_DONATORS && !usersradminDonator)
            {
                continue;
            }

            if (usersradminDonator)
            {
                if (CJFM_DonatorList.isSeniorDonator(player) && TFM_SuperadminList.isSeniorAdmin(player))
                {
                    prefix = (ChatColor.LIGHT_PURPLE + "[Sra + Senior Donator]");
                }
                else
                {
                    prefix = (ChatColor.DARK_AQUA + "[Sra + Donator]");
                }

            }

            boolean useradminDonator = CJFM_DonatorList.isUserDonator(player) && TFM_SuperadminList.isUserSuperadmin(player);

            if (listFilter == ListFilter.SHOW_DONATORS && !useradminDonator)
            {
                continue;
            }

            if (useradminDonator)
            {
                if (TFM_SuperadminList.isSeniorAdmin(player))
                {
                    prefix = (ChatColor.LIGHT_PURPLE + "[Sra + Donator]");
                }
                else
                {
                    prefix = (ChatColor.DARK_AQUA + "[Sa + Donator]");
                }

            }


            player_names.add(prefix + player.getName() + ChatColor.WHITE);
        }

        onlineUsers.append("Connected ").append(listFilter == ListFilter.SHOW_ADMINS ? "admins" : "players").append(": ").append(StringUtils.join(player_names, ", "));

        if (senderIsConsole)
        {
            sender.sendMessage(ChatColor.stripColor(onlineStats.toString()));
            sender.sendMessage(ChatColor.stripColor(onlineUsers.toString()));
        }
        else
        {
            sender.sendMessage(onlineStats.toString());
            sender.sendMessage(onlineUsers.toString());
        }

        return true;
    }
}
