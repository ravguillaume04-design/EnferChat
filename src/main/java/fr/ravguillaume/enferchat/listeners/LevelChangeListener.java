package fr.ravguillaume.enferchat.listeners;

import fr.ravguillaume.enferchat.EnferChatPlugin;
import fr.ravguillaume.enferchat.util.ColorUtil;
import fr.ravguillaume.niveaux.events.PlayerLevelChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.UnaryOperator;

/**
 * Réagit aux changements de niveau exposés par le plugin Niveaux.
 *
 * Raisons gérées :
 *  PLAYTIME     → message level-up + broadcast optionnel
 *  DEATH_RESET  → notification de perte de niveaux
 *  ADMIN_ADD    → notification d'ajout admin
 *  ADMIN_REMOVE → notification de retrait admin
 *  ADMIN_SET    → notification de définition de niveau
 *  ADMIN_RESET  → notification de reset admin
 */
public class LevelChangeListener implements Listener {

    private final EnferChatPlugin plugin;

    public LevelChangeListener(EnferChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLevelChange(PlayerLevelChangeEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayerUuid());

        switch (event.getReason()) {
            case PLAYTIME     -> handleLevelUp(player, event);
            case DEATH_RESET  -> handleDeathReset(player, event);
            case ADMIN_ADD    -> handleAdminChange(player, event, "admin-add");
            case ADMIN_REMOVE -> handleAdminChange(player, event, "admin-remove");
            case ADMIN_SET    -> handleAdminSet(player, event);
            case ADMIN_RESET  -> handleAdminReset(player, event);
        }
    }

    // -------------------------------------------------------------------------

    private void handleLevelUp(Player player, PlayerLevelChangeEvent event) {
        if (player != null) {
            sendIfNotEmpty(player,
                    plugin.getConfig().getString("formats.level-up.player", ""),
                    s -> s.replace("{level}", String.valueOf(event.getNewLevel()))
                          .replace("{old_level}", String.valueOf(event.getOldLevel())));
        }

        String broadcast = plugin.getConfig().getString("formats.level-up.broadcast", "");
        if (!broadcast.isBlank()) {
            Bukkit.broadcastMessage(ColorUtil.colorize(broadcast
                    .replace("{name}",      event.getPlayerName())
                    .replace("{level}",     String.valueOf(event.getNewLevel()))
                    .replace("{old_level}", String.valueOf(event.getOldLevel()))));
        }
    }

    private void handleDeathReset(Player player, PlayerLevelChangeEvent event) {
        if (player == null) return;
        sendIfNotEmpty(player,
                plugin.getConfig().getString("formats.death-reset.player", ""),
                s -> s.replace("{old_level}", String.valueOf(event.getOldLevel())));
    }

    private void handleAdminChange(Player player, PlayerLevelChangeEvent event, String key) {
        if (player == null) return;
        int amount = Math.abs(event.getNewLevel() - event.getOldLevel());
        sendIfNotEmpty(player,
                plugin.getConfig().getString("formats." + key + ".player", ""),
                s -> s.replace("{amount}",    String.valueOf(amount))
                      .replace("{old_level}", String.valueOf(event.getOldLevel()))
                      .replace("{level}",     String.valueOf(event.getNewLevel())));
    }

    private void handleAdminSet(Player player, PlayerLevelChangeEvent event) {
        if (player == null) return;
        sendIfNotEmpty(player,
                plugin.getConfig().getString("formats.admin-set.player", ""),
                s -> s.replace("{old_level}", String.valueOf(event.getOldLevel()))
                      .replace("{level}",     String.valueOf(event.getNewLevel())));
    }

    private void handleAdminReset(Player player, PlayerLevelChangeEvent event) {
        if (player == null) return;
        sendIfNotEmpty(player,
                plugin.getConfig().getString("formats.admin-reset.player", ""),
                s -> s.replace("{old_level}", String.valueOf(event.getOldLevel())));
    }

    // -------------------------------------------------------------------------

    /** Envoie un message colorisé au joueur uniquement si le template est non vide. */
    private void sendIfNotEmpty(Player player, String template, UnaryOperator<String> replacer) {
        if (template.isBlank()) return;
        player.sendMessage(ColorUtil.colorize(replacer.apply(template)));
    }
}
