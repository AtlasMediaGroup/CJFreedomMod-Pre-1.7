package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "System Administration Emergancy", usage = "/<command> <1 |2 |3| 4| Off>>")
public class Command_emg extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if (!TFM_Util.SYSADMINS.contains(sender.getName()))
        {
            sender.sendMessage(TotalFreedomMod.MSG_NO_PERMS);
            TFM_Util.adminAction("WARNING: " + sender.getName(), "Has attempted to use a system admin only command. System administration team has been alerted.", true);
            sender.setOp(false);

            return true;
        }

        if (args.length == 0)
        {
            return false;
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("1"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 1 security lockdown.", true);
                TotalFreedomMod.lockdownEnabled = true;
            }

            if (args[0].equalsIgnoreCase("2"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 2 security lockdown.", true);
                TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);


            }
            
            if (args[0].equalsIgnoreCase("3"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 3 security lockdown.", true);
                TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Has activated the level 3 lockdown, activating admin mode and removing all operator access..", true);
                for (OfflinePlayer player : server.getOperators())
                {
                    player.setOp(false);

                    if (player.isOnline())
                    {
                        player.getPlayer().sendMessage(TotalFreedomMod.YOU_ARE_NOT_OP);
                    }
                }

            }

            if (args[0].equalsIgnoreCase("4"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 4 security lockdown.", true);
                TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);
                for (Player player : server.getOnlinePlayers())
                {
                    if (!TFM_SuperadminList.isUserSuperadmin(player))
                    {
                        player.kickPlayer("Server has initiated a level 4 lockdown. All non super admins have been disconnected..");
                    }
                }
                for (OfflinePlayer player : server.getOperators())
                {
                    player.setOp(false);

                    if (player.isOnline())
                    {
                        player.getPlayer().sendMessage(TotalFreedomMod.YOU_ARE_NOT_OP);
                    }
                }

            }

            if (args[0].equalsIgnoreCase("off"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Security Lockdown Disabled", true);
                TotalFreedomMod.lockdownEnabled = false;
                TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(false);
                for (Player p : server.getOnlinePlayers())
                {
                    if (!p.isOp())
                    {
                        p.setOp(true);
                        p.sendMessage(TotalFreedomMod.YOU_ARE_OP);
                    }
                }

            }

        }
        else if (args.length == 2)
        {
            return false;

        }
        else
        {

            return false;
        }

        return true;
    }
}