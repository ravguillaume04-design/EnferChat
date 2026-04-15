package enferplugins.enferchat.hook;

import enferplugins.niveaux.NiveauxPlugin;
import enferplugins.niveaux.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NiveauxHook {

    private final NiveauxPlugin niveauxPlugin;

    public NiveauxHook() {
        this.niveauxPlugin = (NiveauxPlugin) Bukkit.getPluginManager().getPlugin("Niveaux");
    }

    public int getLevel(UUID uuid) {
        if (niveauxPlugin == null) return 0;
        PlayerData data = niveauxPlugin.getPlayerCache().get(uuid);
        return data != null ? data.getLevel() : 0;
    }

    public int getLevel(Player player) { return getLevel(player.getUniqueId()); }
}
