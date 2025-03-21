package fr.yanis.endmc;

import fr.yanis.endmc.ring.IRing;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class RingManager {

    private HashMap<String, IRing> rings = new HashMap<>();
    private BukkitTask ringTask;

    public void registerRing(IRing ring) {
        rings.put(ring.getIdentifier(), ring);
    }

    public IRing getRing(String identifier) {
        return rings.get(identifier);
    }

    public boolean exists(String identifier) {
        return rings.containsKey(identifier);
    }

    public boolean canEnableRing(String identifier, Player player) {
        IRing ring = getRing(identifier);
        if(ring.isEnabled()){
            player.sendMessage("§cL'anneau spécifié est déjà activé.");
            return false;
        }
        return true;
    }

    public boolean canDisableRing(String identifier, Player player) {
        IRing ring = getRing(identifier);
        if(!ring.isEnabled()){
            player.sendMessage("§cL'anneau spécifié est déjà désactivé.");
            return false;
        }
        return true;
    }

    public void enableRing(String identifier, Player player) {
        IRing ring = getRing(identifier);
        if (ring != null) {
            ring.setPlayer(player);
            ring.onEnable();
            ring.setEnabled(true);
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
                ring.onTick(ring.getElapsedTicks());
            }
        });
    }

    public HashMap<String, IRing> getRings() {
        return rings;
    }

    public void launchTask(){
        this.ringTask = new BukkitRunnable() {
            @Override
            public void run() {
                tickRings();
            }
        }.runTaskTimer(EMain.getInstance(), 0, 1L);
    }

    public void stopTask(){
        if (ringTask != null) {
            ringTask.cancel();
        }
    }

    /**
     * Recherche et enregistre automatiquement tous les rings dans le package "fr.yanis.endmc.ring".
     * Attention : cela suppose que chaque classe IRing.
     */
    public void autoRegisterRings() {
        Reflections reflections = new Reflections("fr.yanis.endmc.ring");
        Set<Class<? extends IRing>> ringClasses = reflections.getSubTypesOf(IRing.class);
        for (Class<? extends IRing> ringClass : ringClasses) {
            try {
                IRing ring = ringClass.getConstructor().newInstance();
                registerRing(ring);
                EMain.getInstance().getLogger().info("Successfully registered ring " + ring.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
