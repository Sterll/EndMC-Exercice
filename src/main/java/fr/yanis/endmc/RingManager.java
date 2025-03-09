package fr.yanis.endmc;

import fr.yanis.endmc.ring.IRing;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

public class RingManager {

    private HashMap<String, IRing> rings = new HashMap<>();

    public void registerRing(IRing ring) {
        rings.put(ring.getIdentifier(), ring);
    }

    public IRing getRing(String identifier) {
        return rings.get(identifier);
    }

    public boolean exists(String identifier) {
        return rings.containsKey(identifier);
    }

    public boolean canEnableRing(String identifier) {
        IRing ring = getRing(identifier);
        return ring != null && !ring.isEnabled();
    }

    public boolean canDisableRing(String identifier) {
        IRing ring = getRing(identifier);
        return ring != null && ring.isEnabled();
    }

    public void enableRing(String identifier, Player player) {
        IRing ring = getRing(identifier);
        if (ring != null) {
            ring.onEnable();
            ring.setEnabled(true);
            ring.setPlayer(player);
        }
    }

    public void disableRing(String identifier) {
        IRing ring = getRing(identifier);
        if (ring != null) {
            ring.onDisable();
            ring.setEnabled(false);
            ring.setPlayer(null);
        }
    }

    public void tickRings() {
        rings.values().forEach(ring -> {
            if (ring.isEnabled()) {
                ring.onTick();
            }
        });
    }

}
