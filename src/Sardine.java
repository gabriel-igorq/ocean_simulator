import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * 
 * @author Gabriel Igor e Victor Hugo
 *
 */
public class Sardine extends Fish{
		// The age at which a tuna can start to breed.
	    private static final int BREEDING_AGE = 2;
	    // The age to which a tuna can live.
	    private static final int MAX_AGE = 85;
	    // The likelihood of a tuna breeding.
	    private static final double BREEDING_PROBABILITY = 0.6;
	    // The maximum number of tunas.
	    private static final int MAX_LITTER_SIZE = 14;
	    // The food value of a single sardine. In effect, this is the
	    // number of steps a tuna can go before it has to eat again.
	    private static final int SEAWEED_FOOD_VALUE = 10;
	    // A shared random number generator to control breeding.
	    private static final Random rand = Randomizer.getRandom();


	    /**
	     * Create a tuna. A tuna can be created as a new born (age zero
	     * and not hungry) or with a random age and food level.
	     * 
	     * @param randomAge If true, the tuna will have random age and hunger level.
	     * @param field The field currently occupied.
	     * @param location The location within the field.
	     */
	    public Sardine(boolean randomAge, Ocean field, Location location){
	    	super(field, location);
	        setAge(0);
	        setAlive(true);
	        if(randomAge) {
	            setAge(rand.nextInt(MAX_AGE));
	            setFoodLevel(rand.nextInt(SEAWEED_FOOD_VALUE));
	        }
	        else {
	            // leave age at 0
	            setFoodLevel(SEAWEED_FOOD_VALUE);
	        }
	    }
	    
	    /**
	     * This is what the tuna does most of the time: it hunts for
	     * sardines. In the process, it might breed, die of hunger,
	     * or die of old age.
	     * @param field The field currently occupied.
	     * @param newTunas A list to add newly born tunas to.
	     */
	    public void act(List<Actor> newSardines)
	    {
	    	incrementAge();
	        incrementHunger();
	        if(isAlive()) {
	            giveBirth(newSardines);            
	            Location newLocation = findFood(getLocation());
	            if(newLocation == null) {
	            	newLocation = getField().freeAdjacentLocation(getLocation());
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

	    /*
	    private void incrementAge()
	    {
	        setAge(getAge() + 1);
	        if(getAge() > MAX_AGE) {
	            setDead();
	        }
	    }
	    */
	    
	    /**
	     * Make this tuna more hungry. This could result in the tuna's death.
	     */
	    private void incrementHunger()
	    {
	        setFoodLevel(getFoodLevel() - 1);
	        if(getFoodLevel() <= 0) {
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
	    	Ocean ocean = getOcean();
			List<Location> adjacent = ocean.adjacentLocations(getLocation());
			Iterator<Location> it = adjacent.iterator();
			while(it.hasNext()) {
				Location where = it.next();
				Object seaweed = ocean.getSeaweedAt(where.getRow(), where.getCol());
				if(seaweed instanceof Seaweed && ocean.getFishAt(where) == null) {
					Seaweed food = (Seaweed) seaweed;
					setFoodLevel(food.getValue());
					food.dead();
					return where;
				}
			}
			return null;
	    }
	    
	    /**
	     * Check whether or not this tuna is to give birth at this step.
	     * New births will be made into free adjacent locations.
	     * @param newTunas A list to add newly born foxes to.
	     */
	    private void giveBirth(List<Actor> newTunas)
	    {
	        // New tunas are born into adjacent locations.
	        // Get a list of adjacent free locations.
	    	Ocean field = getField();
	        List<Location> free = field.getFreeAdjacentLocations(getLocation());
	        int births = breed();
	        for(int b = 0; b < births && free.size() > 0; b++) {
	            Location loc = free.remove(0);
	            Sardine young = new Sardine(false, field, loc);
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
	    /*
	    private boolean canBreed()
	    {
	        return getAge() >= getBreedingAge();
	    }
	*/
	    public int getBreedingAge() {
	    	return BREEDING_AGE;
	    }
	    
	    public int getMaxAge() {
	    	return MAX_AGE;
	    }
	    
	    /**
	     * Indicate that the tuna is no longer alive.
	     * It is removed from the field.
	     */
	    
	}