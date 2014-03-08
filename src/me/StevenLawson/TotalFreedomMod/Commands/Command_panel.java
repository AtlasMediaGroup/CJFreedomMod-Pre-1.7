package me.StevenLawson.TotalFreedomMod.Commands;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static me.RyanWild.CJFreedomMod.CJFM_Util.SYSADMINS;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Panel management.", usage = "/<command> <addme | admin>")
public class Command_panel extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1 && args[0].equalsIgnoreCase("addme"))
        {
            String rank;
            String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
            int numberOfCodes = 0;
            String password = "";
            while (numberOfCodes < 6)
            {
                    char c = chars.charAt((int) (Math.random() * chars.length()));
                    password += c;
                    numberOfCodes++;
            }
                
            if (TFM_SuperadminList.isSeniorAdmin(sender))
            {
                rank = "Senior";
            }
            
            else
            {
                rank = "Super";
            }
            
            playerMsg(sender, "Congratulations, you now have " + rank + " admin access to the CJFreedom panel! Your password is " + password + "! Be sure to change it quickly!");
            
            try
            {
                plugin.updateDatabase("INSERT INTO cjf_panel_users (username, password, rank) VALUES ('" + sender.getName() + "', '" + password + "', '" + rank + "');");
            }
            catch (SQLException ex)
            {
                Logger.getLogger(Command_panel.class.getName()).log(Level.SEVERE, null, ex);
                playerMsg(sender, "Error updating MySQL Table contact a System Admin or Developer");
            }
            
            return true;
        }
        
        if (args.length == 2 && args[0].equalsIgnoreCase("admin"))
        {
            if (!SYSADMINS.contains(sender.getName()))
            {
                sender.sendMessage(TotalFreedomMod.MSG_NO_PERMS);
                return true;
            }
            
            if (args[1].equalsIgnoreCase("disable"))
            {
                /* - Future feature
                try
                {
                    plugin.updateDatabase("UPDATE cjf_panel_users SET disabled='false' WHERE username='" + args[2] + "';");
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(Command_panel.class.getName()).log(Level.SEVERE, null, ex);
                    playerMsg(sender, "Error updating MySQL Table contact a System Admin or Developer");
                }
                return true;
                */
            }
            
            if (args[1].equalsIgnoreCase("delete"))
            {
                try
                {
                    plugin.updateDatabase("DELETE FROM cjf_panel_users WHERE username='" + args[2] + "';");
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(Command_panel.class.getName()).log(Level.SEVERE, null, ex);
                    playerMsg(sender, "Error updating MySQL Table contact a System Admin or Developer");
                }
                return true;
            }
        }
        return false;
    }
}



