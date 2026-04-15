package fr.ravguillaume.enferchat.listeners;

import fr.ravguillaume.enferchat.EnferChatPlugin;
import fr.ravguillaume.enferchat.util.ColorUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Remplace le format de chat par défaut.
 * Format configurable : [niveau] pseudo : message
 *
 * Placeholders config : {level}, {name}, {message}
 */
public class ChatListener implements Listener {

    private final EnferChatPlugin plugin;

    public ChatListener(EnferChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        int level = plugin.getNiveauxHook() != null
                ? plugin.getNiveauxHook().getLevel(event.getPlayer())
                : 0;

        // Colorisation optionnelle des messages joueurs
        if (plugin.getConfig().getBoolean("formats.allow-chat-colors", false)) {
            event.setMessage(ColorUtil.colorize(event.getMessage()));
        }

        String template = plugin.getConfig().getString(
                "formats.chat",
                "&#606060[&#FFB300{level}&#606060] &#FFFFFF{name} &#606060: &#FFFFFF{message}");

        // 1. Remplacer {level} et {name} (valeurs sûres, pas de codes couleur)
        // 2. Coloriser le format
        // 3. Remplacer {message} par %2$s APRÈS colorisation (le contenu du message
        //    est injecté par Bukkit via String.format — jamais colorisé ici)
        String formatted = template
                .replace("{level}", String.valueOf(level))
                .replace("{name}", event.getPlayer().getName());

        formatted = ColorUtil.colorize(formatted);
        formatted = formatted.replace("{message}", "%2$s");

        event.setFormat(formatted);
    }
}
