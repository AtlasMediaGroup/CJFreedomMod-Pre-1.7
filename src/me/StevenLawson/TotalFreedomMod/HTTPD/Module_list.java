package me.StevenLawson.TotalFreedomMod.HTTPD;

import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Module_list extends TFM_HTTPD_Module
{
    public Module_list(NanoHTTPD.HTTPSession session)
    {
        super(session);
    }

    @Override
    public String getBody()
    {
        final StringBuilder body = new StringBuilder();

        final Player[] onlinePlayers = Bukkit.getOnlinePlayers();

        body.append("<p>There are currently ").append(onlinePlayers.length).append("/").append(Bukkit.getMaxPlayers()).append(" players online:</p>\r\n");

        body.append("<ul>\r\n");

        for (Player player : onlinePlayers)
        {
            String prefix = "";
            if (TFM_SuperadminList.isUserSuperadmin(player))
            {
                              
                if (TFM_SuperadminList.isSeniorAdmin(player))
                {
                    prefix = "[SrA]";
                }
                else
                {
                    prefix = "[SA]";
                }                
                
                if (TFM_Util.DEVELOPERS.contains(player.getName()))
                {
                    prefix = "[Dev]";
                }
                
                if (player.getName().equalsIgnoreCase("wild1145"))
                {
                    prefix =  "[Chief Developer & System Admin]";
                }
                
                if (player.getName().equalsIgnoreCase("thecjgcjg"))
                {
                    prefix = "[Retired Owner & System Admin]";
                }
                if (player.getName().equalsIgnoreCase("DarthSalamon"))
                {
                    prefix = "[System Admin & TFM Guru]";
                }
                
                if (player.getName().equalsIgnoreCase("Varuct"))
                {
                    prefix = "[Owner & System Admin]";
                }
                
                 if (player.getName().equalsIgnoreCase("markbyron"))
                {
                    prefix = "[TotalFreedom Owner]";
                }
                
                if (player.getName().equalsIgnoreCase("camzie99"))
                {
                    prefix = "[Executive Chief of Development]";
                }
                if (player.getName().equalsIgnoreCase("kyled1986"))
                {
                    prefix = "[Executive Chief Creative Designer]";
                }
            }
            else
            {
                if (player.isOp())
                {
                    prefix = "[OP]";
                }
            }

            body.append("<li>").append(prefix).append(player.getName()).append("</li>\r\n");
        }

        body.append("</ul>\r\n");

        return body.toString();
    }

    @Override
    public String getTitle()
    {
        return "CJFreedom - Current Online Users";
    }
}
