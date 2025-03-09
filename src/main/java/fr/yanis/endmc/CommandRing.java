package fr.yanis.endmc;

import fr.yanis.endmc.ring.IRing;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRing implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Seul un joueur peut exécuter cette commande.");
            return false;
        }

        switch (args.length){
            case 0:
                player.sendMessage("§cVeillez préciser si vous souhaitez désactiver ou activer un anneau.");
                return true;
            case 1:
                String action = args[0];
                if (action.equalsIgnoreCase("enable") || action.equalsIgnoreCase("disable")) {
                    player.sendMessage("§cVeillez préciser l'anneau que vous souhaitez activer ou désactiver.");
                } else {
                    player.sendMessage("§cAction inconnue. Utilisez 'enable' ou 'disable'.");
                }
                return true;
            case 2:
                String action_identifier = args[0];

                if (!(action_identifier.equalsIgnoreCase("enable") || action_identifier.equalsIgnoreCase("disable"))) {
                    player.sendMessage("§cAction inconnue. Utilisez 'enable' ou 'disable'.");
                    return true;
                }

                RingManager ringManager = EMain.getInstance().getRingManager();

                String ring__identifier = args[1];
                if (ringManager.exists(ring__identifier)){
                    player.sendMessage("§cL'anneau spécifié n'existe pas.");
                    return false;
                } if (action_identifier.equalsIgnoreCase("enable") && ringManager.canEnableRing(ring__identifier, player)) {
                    ringManager.enableRing(ring__identifier, player);
                } else if (action_identifier.equalsIgnoreCase("disable") && ringManager.canDisableRing(ring__identifier, player)) {
                    ringManager.disableRing(ring__identifier);
                }
        }

        return false;
    }
}
