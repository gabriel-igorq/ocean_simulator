import java.util.List;

/**
 *Classe baseada em Animal, do projeto fox and rabbits
 */
public abstract class Fish{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Ocean field;
    // The animal's position in the field.
    private Location location;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(Ocean field, Location location){
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to add newly born animals to.
     */
    abstract public void act(List<Fish> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    public void setDead(){
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Ocean getField(){
        return field;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation){
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}