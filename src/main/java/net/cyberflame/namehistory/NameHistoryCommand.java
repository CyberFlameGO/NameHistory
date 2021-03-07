package net.cyberflame.namehistory;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NameHistoryCommand extends Command {

    NameHistory plugin;

    public NameHistoryCommand(NameHistory plugin) {
        super("nh");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (sender.hasPermission("namehistory.lookup")) {
            if (args.length >= 1) {
                ProxiedPlayer victim = ProxyServer.getInstance().getPlayer(args[0]);
                if (victim != null) {
                    plugin.doRequest(victim.getUniqueId().toString(), p);
                } else {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Usage: /nh <player>");
            }
        }
    }
}
