package enferplugins.enferchat.listeners;

import enferplugins.enferchat.EnferChatPlugin;
import enferplugins.enferchat.util.ColorUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final EnferChatPlugin plugin;
    public ChatListener(EnferChatPlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        int level = plugin.getNiveauxHook() != null ? plugin.getNiveauxHook().getLevel(event.getPlayer()) : 0;
        if (plugin.getConfig().getBoolean("formats.allow-chat-colors", false))
            event.setMessage(ColorUtil.colorize(event.getMessage()));
        String template = plugin.getConfig().getString("formats.chat",
            "&#606060[&#FFB300{level}&#606060] &#FFFFFF{name} &#606060: &#FFFFFF{message}");
        String formatted = template.replace("{level}", String.valueOf(level)).replace("{name}", event.getPlayer().getName());
        formatted = ColorUtil.colorize(formatted).replace("{message}", "%2$s");
        event.setFormat(formatted);
    }
}
