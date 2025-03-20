package fr.yanis.endmc.ring;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IRing {

    String getName();
    String getIdentifier();
    void onTick(long elapsedTicks);
    void onEnable();
    void onDisable();

    boolean isEnabled();
    void setEnabled(boolean enabled);

    Player getPlayer();
    void setPlayer(Player user);

    long getElapsedTicks();

}
