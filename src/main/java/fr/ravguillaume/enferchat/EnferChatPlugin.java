package fr.ravguillaume.enferchat;

import fr.ravguillaume.enferchat.hook.NiveauxHook;
import fr.ravguillaume.enferchat.listeners.ChatListener;
import fr.ravguillaume.enferchat.listeners.DeathListener;
import fr.ravguillaume.enferchat.listeners.LevelChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Point d'entrée du plugin EnferChat.
 *
 * Gère :
 *  - Le format du chat ([niveau] pseudo : message)
 *  - Les messages de mort avec affichage du niveau
 *  - Les notifications de changement de niveau (soft-depend Niveaux)
 */
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

        if (niveauxHook != null) {
            Bukkit.getPluginManager().registerEvents(new LevelChangeListener(this), this);
        }

        getLogger().info("EnferChat v" + getDescription().getVersion() + " activé.");
    }

    @Override
    public void onDisable() {
        getLogger().info("EnferChat désactivé.");
    }

    /** Retourne le hook Niveaux, ou null si le plugin Niveaux est absent. */
    public NiveauxHook getNiveauxHook() {
        return niveauxHook;
    }
}
