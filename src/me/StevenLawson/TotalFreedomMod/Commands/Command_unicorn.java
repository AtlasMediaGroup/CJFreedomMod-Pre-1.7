
package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author hawkeyeshi
 */

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Display a magical unicorn to everyone's chat", usage = "/<command>")
public class Command_unicorn extends TFM_Command
{
    
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        
        if(args.length != 0)
        {
            playerMsg(ChatColor.DARK_RED + "Incorrect args!");
        }
        else if(args.length == 0)
        {
            for (Player player : server.getOnlinePlayers())
            {
                playerMsg(TFM_Util.randomChatColor() + "                                                         ,/");
                playerMsg(TFM_Util.randomChatColor() + "                                                        //");
                playerMsg(TFM_Util.randomChatColor() + "                                                      ,//");
                playerMsg(TFM_Util.randomChatColor() + "                                          ___   /|   |//");
                playerMsg(TFM_Util.randomChatColor() + "                                      `__/\\_ --(/|___/-/");
                playerMsg(TFM_Util.randomChatColor() + "                                   \\|\\_-\\___ __-_`- /-/ \\.");
                playerMsg(TFM_Util.randomChatColor() + "                                  |\\_-___,-\\_____--/_)' ) \\");
                playerMsg(TFM_Util.randomChatColor() + "                                   \\ -_ /     __ \\( `( __`\\|");
                playerMsg(TFM_Util.randomChatColor() + "                                   `\\__|      |\\)\\ ) /(/|");
                playerMsg(TFM_Util.randomChatColor() + "           ,._____.,            ',--//-|      \\  |  '   /");
                playerMsg(TFM_Util.randomChatColor() + "          /     __. \\,          / /,---|       \\       /");
                playerMsg(TFM_Util.randomChatColor() + "        |  | ( (  \\   |      ,/\\'__/'/          |     |");
                playerMsg(TFM_Util.randomChatColor() + "        |  \\  \\`--, `_/_------______/           \\(   )/");
                playerMsg(TFM_Util.randomChatColor() + "        | | \\  \\_. \\,                            \\___/\\");
                playerMsg(TFM_Util.randomChatColor() + "        | |  \\_   \\  \\                                 \\");
                playerMsg(TFM_Util.randomChatColor() + "        \\ \\    \\_ \\   \\   /                             \\");
                playerMsg(TFM_Util.randomChatColor() + "         \\ \\  \\._  \\__ \\_|       |                       \\");
                playerMsg(TFM_Util.randomChatColor() + "          \\ \\___  \\      \\       |                        \\");
                playerMsg(TFM_Util.randomChatColor() + "           \\__ \\__ \\  \\_ |       \\                         |");
                playerMsg(TFM_Util.randomChatColor() + "           |  \\_____ \\  ____      |                           |");
                playerMsg(TFM_Util.randomChatColor() + "           | \\  \\__ ---' .__\\     |        |                 |");
                playerMsg(TFM_Util.randomChatColor() + "           \\  \\__ ---   /   )     |        \\                /");
                playerMsg(TFM_Util.randomChatColor() + "            \\   \\____/ / ()(      \\          `---_         /|");
                playerMsg(TFM_Util.randomChatColor() + "             \\__________/(,--__    \\_________.    |       ./ |");
                playerMsg(TFM_Util.randomChatColor() + "               |     \\ \\  `---_\\--,           \\   \\_,./   |");
                playerMsg(TFM_Util.randomChatColor() + "               |      \\  \\_ ` \\    /`---_______-\\   \\\\    /");
                playerMsg(TFM_Util.randomChatColor() + "                \\      \\.___,`|   /              \\   \\\\   \\");
                playerMsg(TFM_Util.randomChatColor() + "                 \\     |  \\_ \\|   \\              (   |:    |");
                playerMsg(TFM_Util.randomChatColor() + "                  \\    \\      \\    |             /  / |    ;");
                playerMsg(TFM_Util.randomChatColor() + "                   \\    \\      \\    \\          ( `_'   \\  |");
                playerMsg(TFM_Util.randomChatColor() + "                    \\.   \\      \\.   \\          `__/   |  |");
                playerMsg(TFM_Util.randomChatColor() + "                      \\   \\       \\.  \\                |  |");
                playerMsg(TFM_Util.randomChatColor() + "                       \\   \\        \\  \\               (  )");
                playerMsg(TFM_Util.randomChatColor() + "                        \\   |        \\  |                |  |");
                playerMsg(TFM_Util.randomChatColor() + "                         |  \\         \\ \\               I  `");
                playerMsg(TFM_Util.randomChatColor() + "                         ( __;        ( _;                ('-_';");
                playerMsg(TFM_Util.randomChatColor() + "                         |___\\       \\___:              \\___:");
                
                playerMsg("   ");
                playerMsg("   ");
                playerMsg(TFM_Util.randomChatColor() + "You've been " + TFM_Util.randomChatColor() + "unicorned!");
                player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 1.0F, 1.0F);
            }
        }
        
        return false;
        
    }
}
