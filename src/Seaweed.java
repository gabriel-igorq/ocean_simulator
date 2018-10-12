import java.util.List;
import java.util.Random;

/**
 * A simple model of a sardine.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Seaweed extends Cell implements Actor
{
    // Characteristics shared by all sardines (static fields).
	private int value;
    // The age at which a sardine can start to breed.
    //private static final int BREEDING_AGE = 5;
    // The age to which a sardine can live.
    //private static final int MAX_AGE = 40;
    // The likelihood of a sardine breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The sardine's age.
    //private int age;

    /**
     * Create a new sardine. A sardine may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Seaweed(boolean randomAge, Ocean field, Location location)
    {
        super(field, location);
        value = 0;
        if(randomAge) {
            value = rand.nextInt(10);
        }
    }
    
    /**
     * This is what the sardine does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newSardines A list to add newly born rabbits to.
     */
    public void act(List<Actor> actors)
    {
        if(isAlive()) {
        	if(value < 10) {
        		value++;
        	} else if(value == 10) {
        		giveBirth(actors);
        	} 
        }
    }

    /*
    public void insertSeaweed(Location newLocation) {
    	Location location = getLocation();
    	Ocean ocean = getOcean();
    	
    	if(location != null) {
    		ocean.clear(location);
    	}
    	
    	location = newLocation;
    	ocean.place(this, newLocation);
    }
    */
    
    public int getValue() {
    	return value;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
    /**
     * Increase the age.
     * This could result in the sardine's death.
     */
    /*
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    */
    
    /**
     * Check whether or not this sardine is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to add newly born rabbits to.
     */
    private void giveBirth(List<Actor> newSardines)
    {
        // New sardines are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Seaweed young = new Seaweed(false, field, loc);
            newSardines.add(young);
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
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    /**
     * A sardine can breed if it has reached the breeding age.
     * @return true if the sardine can breed, false otherwise.
     */
    /*
    private boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    */
    /*
    public int getBreedingAge() {
    	return BREEDING_AGE;
    }
    */
}
