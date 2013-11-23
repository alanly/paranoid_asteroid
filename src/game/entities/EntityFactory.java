package game.entities;

import game.Point;
import game.enums.Size;
/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 11/23/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityFactory {

      private static EntityFactory factory;

    public static EntityFactory getInstance(){
        if( factory == null)
             factory = new EntityFactory();
        return factory;
    }

    public Alien makeAlien(Point center, Entity target){
        return new Alien(center, target);
    }
    public Bullet makeBullet(Entity target, Point center, double angle){
        return new Bullet(target,center,angle);
    }
    public Powerup makePowerUp(Point center){
        return new Powerup(center);
    }
    public Ship makeShip(Point p) {
        return new Ship(p);
    }
    public Particle makeParticle(Point p) {
        return new Particle(p);
    }
    /**
     * Builds an Asteroid of specified size and location.
     * @param size desired size of the Asteroid being returned
     * @param center desired location of the Asteroid being returned
     * @return Built Asteroid object.
     */
    public Asteroid makeAsteroid(Size size, Point center) {
        if (size == Size.SMALL) {
            return new SmallAsteroid(center);
        } else if (size == Size.MEDIUM) {
            return new MediumAsteroid(center);
        } else {
            return new LargeAsteroid(center);
        }
    }
}
