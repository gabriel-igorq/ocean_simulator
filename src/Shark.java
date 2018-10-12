import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A simple model of a shark.
 * Sharks age, move, breed, and die.
 * Sharks eat groper or herring but they prefer groper.
 * Sharks are loners - they prefer not to swim next to each other
 * @author Richard Jones and Michael Kolling
 */
public class Shark extends Fish
{
 // Characteristics shared by all sharks (static fields).
    
    // The age at which a shark can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a shark can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a shark breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single tuna. In effect, this is the
    // number of steps a shark can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 6;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Create a shark. A shark can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the shark will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Shark(boolean randomAge, Ocean field, Location location)
    {
        super(field, location);
        setAge (0);
        setAlive(true);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(RABBIT_FOOD_VALUE));
        }
        else {
        	setAge(0);
        	setFoodLevel(RABBIT_FOOD_VALUE);
        }
    }
    
    /**
     * This is what the sharks does most of the time: it hunts for
     * food. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newSharks A list to add newly born sharks to.
     */
    public void act(List<Actor> newSharks)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSharks);            
            // Move towards a source of food if found.
          //  Location location = getLocation();
            Location newLocation = findFood(getLocation());
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Increase the age. This could result in the shark's death.
     */
    /*
    private void incrementAge()
    {
    	setAge(getAge()+1);
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }
    */
    
    /**
     * Make this shark more hungry. This could result in the sharks's death.
     */
    private void incrementHunger()
    {
    	setFoodLevel(getFoodLevel()-1);
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
    /**
     * Tell the sharks to look for tunas adjacent to its current location.
     * Only the first live tuna or sardine is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        List<Location> adjacent = getField().adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Cell animal = getField().getFishAt(where);
            if(animal instanceof Tuna) {
                Tuna tuna = (Tuna) animal;
                if(tuna.isAlive()) { 
                    tuna.setDead();
                    setFoodLevel(RABBIT_FOOD_VALUE);
                    // Remove the dead tuna from the field.
                    return where;
                }
            } else if(animal instanceof Sardine) {
                Sardine sardine = (Sardine) animal;
                if(sardine.isAlive()) { 
                    sardine.setDead();
                    setFoodLevel(RABBIT_FOOD_VALUE);
                    // Remove the dead tuna from the field.
                    return where;
                }
            } else if(animal instanceof Shark) {
                Shark shark = (Shark) animal;
                if(shark.isAlive()) { 
                	Location aux = getField().freeAdjacentLocation(where);
                	Location aux2 = getField().freeAdjacentLocation(aux);
                	return aux2;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this shark is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSharks A list to add newly born sharks to.
     */
    private void giveBirth(List<Actor> newSharks)
    {
        // New sharks are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Shark young = new Shark(false, getField(), loc);
            newSharks.add(young);
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
     * A shark can breed if it has reached the breeding age.
     */
    /*
    private boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
    }
    */
    
    public int getBreedingAge() {
    	return BREEDING_AGE;
    }
    
    public int getMaxAge() {
    	return MAX_AGE;
    }
}
