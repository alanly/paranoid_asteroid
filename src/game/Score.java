package game;

import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Entity;
import game.entities.Ship;

public class Score {
    // Points constants
    private static final long POINTS_ALIEN = 250;
    private static final long POINTS_ASTEROID = 100;
    private static final long POINTS_CLEAR_LEVEL = 200;

    private long points;
    private long lastLevelPoints;
    private double multiplier;

    /**
     * Increments the points by an amount depending on depending the Entity destroyed and multiplier
     * @param destroyed Entity which has been destroyed or <tt>null</tt> if the point allocation is not a result of Entity destruction
     */
    public void allocatePoints(Entity destroyed, Ship ship) {
        if (destroyed == null) {
            // Not an interaction with an entity
            points += multiplier * POINTS_CLEAR_LEVEL;
            multiplier += 0.5;
        } else {
            // Tell the ship it hit something
            ship.bulletHit();

            // Allocate points based on what entity was destroyed
            if (destroyed instanceof Asteroid) {
                points += multiplier * POINTS_ASTEROID;
            } else if (destroyed instanceof Alien) {
                points += multiplier * POINTS_ALIEN;
            }
        }
    }

    /**
     * Returns the point count
     * @return <tt>points</tt>
     */
    public long getPoints() {
        return points;
    }

    public void setPoints(long l) {
        this.points = l;
    }

    /**
     * Returns the multiplier for this level.
     * @return the multiplier for this level.
     */
    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double d) {
        this.multiplier = d;
    }

    /**
     * Returns the points for the last level.
     * @return the points for the last level.
     */
    public long getLastLevelPoints() {
        return lastLevelPoints;
    }

    public void setLastLevelPoints(long l) {
        this.lastLevelPoints = l;
    }


    public void updatePoints() {
        this.lastLevelPoints = this.points;
    }
}