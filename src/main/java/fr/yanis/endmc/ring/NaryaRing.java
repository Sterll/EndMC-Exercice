package fr.yanis.endmc.ring;

import fr.yanis.endmc.EMain;
import fr.yanis.endmc.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NaryaRing implements IRing {

    private Player player;
    private BukkitRunnable task;
    private double angle = 0.0;
    private double currentHeight = 0.5;
    private boolean enabled = false;

    @Override
    public String getIdentifier() {
        return "narya";
    }

    @Override
    public String getName() {
        return "Narya, l'anneau de feu";
    }

    @Override
    public void onEnable() {
        if (enabled) return;
        enabled = true;

        task = new BukkitRunnable() {
            @Override
            public void run() {
                onTick();
            }
        };
        task.runTaskTimer(EMain.getInstance(), 0L, 2L);
    }

    @Override
    public void onDisable() {
        if (!enabled) return;
        enabled = false;
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player user) {
        this.player = user;
    }

    @Override
    public void onTick() {
        if (player == null || !player.isOnline()) {
            onDisable();
            return;
        }

        Location center = player.getLocation().clone().add(0, currentHeight, 0);
        double radius = 2.0;

        int points = 36;
        for (int i = 0; i < points; i++) {
            double theta = 2 * Math.PI * i / points;
            ParticleUtil.spawnSphericalParticle(center, Particle.FLAME, radius, theta, Math.PI / 2, 1);
        }

        angle += Math.PI / 18;
        if (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }

        currentHeight += 0.1;

        if (currentHeight > 3.0) {
            currentHeight = 0.5;
        }
    }
}
