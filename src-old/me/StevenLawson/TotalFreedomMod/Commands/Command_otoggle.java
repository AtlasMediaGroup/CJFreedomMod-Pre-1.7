package me.StevenLawson.TotalFreedomMod.Commands;
/* - Broken and I need to fix it, but the basis is here :P
import java.util.Arrays;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Superadmin;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import static me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.saveSuperadminList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.ONLY_IN_GAME)
@CommandParameters(
        description = "Toggle whether or not you see admin chat.",
        usage = "/<command>",
        aliases = "adminchattoggle")
public class Command_otoggle extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (TFM_SuperadminList.adminChatEnabled(sender))
        {
            List<String> ips = Arrays.asList(sender_p.getAddress().getAddress().getHostAddress());
            String username = sender.getName().toLowerCase();
            playerMsg(sender, "Admin Chat Visibility Disabled", ChatColor.RED);
            try
            {
                username = username.toLowerCase();

                if (TFM_SuperadminList.superadminList.containsKey(username))
                {
                    TFM_Superadmin superadmin = TFM_SuperadminList.superadminList.get(username);
                    superadmin.setAdminChatEnabled(false);
                }

                saveSuperadminList();
                return true;
            }
            catch (Exception ex)
            {
                TFM_Log.severe(ex);
            }
        }
        
        else if (!TFM_SuperadminList.adminChatEnabled(sender))
        {
            String username = sender.getName().toLowerCase();
            playerMsg(sender, "Admin Chat Visibility Enabled", ChatColor.DARK_AQUA);
            try
            {
                username = username.toLowerCase();

                if (TFM_SuperadminList.superadminList.containsKey(username))
                {
                    TFM_Superadmin superadmin = TFM_SuperadminList.superadminList.get(username);
                    superadmin.setAdminChatEnabled(true);
                }

                saveSuperadminList();
                return true;
            }
            catch (Exception ex)
            {
                TFM_Log.severe(ex);
            }
        }
        return false;
    }
}
*/