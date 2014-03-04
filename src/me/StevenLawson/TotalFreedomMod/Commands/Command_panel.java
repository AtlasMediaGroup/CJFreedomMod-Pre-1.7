package me.StevenLawson.TotalFreedomMod.Commands;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
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
            int numberOfCodes = 0;//controls the length of alpha numberic string
            String password = "";
            while (numberOfCodes < 6)
            {
                    char c = chars.charAt((int) (Math.random() * chars.length()));
                    password += c;
                    numberOfCodes++;
            }
                
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                rank = "super";
            }
            
            else
            {
                rank = "senior";
            }
            
            try
            {
                // Dont think this is correct... plugin.updateDatabase("DELETE FROM cjf_panel_users WHERE username='" + sender.getName() + "';");
                plugin.updateDatabase("INSERT INTO cjf_panel_users (username, password, rank) VALUES ('" + sender.getName() + "', '" + password + "', '" + rank + "');");
            }
            catch (SQLException ex)
            {
                Logger.getLogger(Command_panel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
    }
}



