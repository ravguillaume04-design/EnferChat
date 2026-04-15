package fr.ravguillaume.enferchat.listeners;

import fr.ravguillaume.enferchat.EnferChatPlugin;
import fr.ravguillaume.enferchat.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Remplace le message de mort Minecraft par un format personnalisé.
 *
 * Priorité LOW : le niveau est lu AVANT que Niveaux ne le remette à 0
 * (PlayerDeathListener de Niveaux s'exécute à NORMAL).
 *
 * Placeholders config : {player}, {level}
 * Valeur 'default' → message Minecraft conservé tel quel.
 */
public class DeathListener implements Listener {

    private final EnferChatPlugin plugin;

    public DeathListener(EnferChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        String format = plugin.getConfig().getString("formats.death", "default");

        if (format.equalsIgnoreCase("default")) {
            return;
        }

        Player player = event.getEntity();
        int level = plugin.getNiveauxHook() != null
                ? plugin.getNiveauxHook().getLevel(player)
                : 0;

        String message = format
                .replace("{player}", player.getName())
                .replace("{level}", String.valueOf(level));

        event.setDeathMessage(ColorUtil.colorize(message));
    }
}
