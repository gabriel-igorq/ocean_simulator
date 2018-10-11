import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Tuna extends Fish
{
    // The age at which a tuna can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a tuna can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a tuna breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of tunas.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single sardine. In effect, this is the
    // number of steps a tuna can go before it has to eat again.
    private static final int SARDINE_FOOD_VALUE = 7;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // The tuna's age.
    private int age;
    // Whether the tuna is alive or not.
    private boolean alive;
    // The tuna's position.
    private Location location;
    // The field occupied.
    // The tuna's food level, which is increased by eating sardines.
    private int foodLevel;

    /**
     * Create a tuna. A tuna can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the tuna will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tuna(boolean randomAge, Ocean field, Location location){
    	super(field, location);
        age = 0;
        alive = true;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(SARDINE_FOOD_VALUE);
        }
        else {
            // leave age at 0
            foodLevel = SARDINE_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the tuna does most of the time: it hunts for
     * sardines. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newTunas A list to add newly born tunas to.
     */
    public void act(List<Fish> newTunas)
    {
    	incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newTunas);            
            // Try to move into a free location.
            Location newLocation = findFood(location);
            if(newLocation == null) {
            	newLocation = getField().freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether the tuna is alive or not.
     * @return True if the tuna is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            getField().clear(location);
        }
        location = newLocation;
        getField().place(this, newLocation);
    }

    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this tuna more hungry. This could result in the tuna's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Tell the tuna to look for sardines adjacent to its current location.
     * Only the first live sardine is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        List<Location> adjacent = getField().adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Fish animal = getField().getFishAt(where);
            if(animal instanceof Sardine) {
                Sardine sardine = (Sardine) animal;
                if(sardine.isAlive()) { 
                    sardine.setDead();
                    foodLevel = SARDINE_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this tuna is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newTunas A list to add newly born foxes to.
     */
    private void giveBirth(List<Fish> newTunas)
    {
        // New tunas are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(location);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Tuna young = new Tuna(false, getField(), loc);
            newTunas.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A tuna can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Indicate that the tuna is no longer alive.
     * It is removed from the field.
     */
    
    public void setDead()
    {
        alive = false;
        if(location != null) {
            getField().clear(location);
            location = null;
            setField(null);
        }
    }
}
