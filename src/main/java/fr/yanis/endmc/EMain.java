package fr.yanis.endmc;

import org.bukkit.plugin.java.JavaPlugin;

public final class EMain extends JavaPlugin {

    private RingManager ringManager;

    @Override
    public void onEnable() {
        this.ringManager = new RingManager();
        ringManager.autoRegisterRings();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EMain getInstance() {
        return EMain.getPlugin(EMain.class);
    }

    public RingManager getRingManager() {
        return ringManager;
    }
}
