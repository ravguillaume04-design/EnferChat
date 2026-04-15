package fr.ravguillaume.enferchat.hook;

import fr.ravguillaume.niveaux.NiveauxPlugin;
import fr.ravguillaume.niveaux.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Façade sur le plugin Niveaux.
 * Fournit un accès au cache de niveaux des joueurs.
 * Instancier uniquement si Niveaux est activé.
 */
public class NiveauxHook {

    private final NiveauxPlugin niveauxPlugin;

    public NiveauxHook() {
        this.niveauxPlugin = (NiveauxPlugin) Bukkit.getPluginManager().getPlugin("Niveaux");
    }

    /**
     * Retourne le niveau actuel d'un joueur depuis le cache Niveaux.
     * Retourne 0 si le joueur est introuvable dans le cache.
     */
    public int getLevel(UUID uuid) {
        if (niveauxPlugin == null) return 0;
        PlayerData data = niveauxPlugin.getPlayerCache().get(uuid);
        return data != null ? data.getLevel() : 0;
    }

    public int getLevel(Player player) {
        return getLevel(player.getUniqueId());
    }
}
