package enferplugins.enferchat;

import enferplugins.enferchat.hook.NiveauxHook;
import enferplugins.enferchat.listeners.ChatListener;
import enferplugins.enferchat.listeners.DeathListener;
import enferplugins.enferchat.listeners.LevelChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnferChatPlugin extends JavaPlugin {

    private NiveauxHook niveauxHook;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (Bukkit.getPluginManager().isPluginEnabled("Niveaux")) {
            niveauxHook = new NiveauxHook();
            getLogger().info("Plugin Niveaux détecté — intégration des niveaux activée.");
        } else {
            getLogger().info("Plugin Niveaux absent — intégration des niveaux désactivée.");
        }
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);
        if (niveauxHook != null) Bukkit.getPluginManager().registerEvents(new LevelChangeListener(this), this);
        getLogger().info("EnferChat v" + getDescription().getVersion() + " activé.");
    }

    @Override
    public void onDisable() { getLogger().info("EnferChat désactivé."); }

    public NiveauxHook getNiveauxHook() { return niveauxHook; }
}
