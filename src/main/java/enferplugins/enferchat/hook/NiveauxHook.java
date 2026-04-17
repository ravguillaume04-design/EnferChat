package enferplugins.enferchat.hook;

import enferplugins.enfercoeur.api.NiveauxProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class NiveauxHook {

    private final NiveauxProvider provider;

    public NiveauxHook() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Niveaux");
        this.provider = (plugin instanceof NiveauxProvider) ? (NiveauxProvider) plugin : null;
    }

    public int getLevel(UUID uuid) {
        return provider != null ? provider.getLevel(uuid) : 0;
    }

    public int getLevel(Player player) { return getLevel(player.getUniqueId()); }
}
