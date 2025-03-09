package fr.yanis.endmc;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticleUtil {

    /**
     * Convertit des coordonnées sphériques en coordonnées cartésiennes.
     *
     * @param center La position centrale (origine de l’orbite).
     * @param r      Le rayon (distance du centre).
     * @param theta  L'angle azimutal (en radians, autour de l'axe vertical).
     * @param phi    L'angle polaire (en radians, par rapport à l'axe vertical).
     * @return Une nouvelle Location correspondant à la position calculée.
     */
    public static Location getLocationFromSpherical(Location center, double r, double theta, double phi) {
        double xOffset = r * Math.sin(phi) * Math.cos(theta);
        double yOffset = r * Math.cos(phi);
        double zOffset = r * Math.sin(phi) * Math.sin(theta);
        return center.clone().add(xOffset, yOffset, zOffset);
    }

    /**
     * Affiche une particule à la position calculée selon les coordonnées sphériques.
     *
     * @param center   La position centrale (par exemple, la position du joueur).
     * @param particle Le type de particule à afficher.
     * @param r        Le rayon (distance du centre).
     * @param theta    L'angle azimutal (en radians).
     * @param phi      L'angle polaire (en radians).
     * @param count    Le nombre de particules à générer.
     */
    public static void spawnSphericalParticle(Location center, Particle particle, double r, double theta, double phi, int count) {
        Location particleLoc = getLocationFromSpherical(center, r, theta, phi);
        World world = center.getWorld();
        if (world != null) {
            world.spawnParticle(particle, particleLoc, count);
        }
    }

}
