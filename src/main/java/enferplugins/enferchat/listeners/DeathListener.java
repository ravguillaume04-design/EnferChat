package enferplugins.enferchat.listeners;

import enferplugins.enferchat.EnferChatPlugin;
import enferplugins.enferchat.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final EnferChatPlugin plugin;
    public DeathListener(EnferChatPlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        String format = plugin.getConfig().getString("formats.death", "default");
        if (format.equalsIgnoreCase("default")) return;
        Player player = event.getEntity();
        int level = plugin.getNiveauxHook() != null ? plugin.getNiveauxHook().getLevel(player) : 0;
        event.setDeathMessage(ColorUtil.colorize(format.replace("{player}", player.getName()).replace("{level}", String.valueOf(level))));
    }
}
