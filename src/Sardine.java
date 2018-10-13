import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Uma classe que representa Sardinhas. Por ser um tipo de peixe, herda
 * da classe Fish.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public class Sardine extends Fish{
		// Idade na qual as sardinha se reproduzem
	    private static final int BREEDING_AGE = 2;
	    // Idade m�xima que sardinhas vivem
	    private static final int MAX_AGE = 85;
	    // Probabilidade de se reproduzir
	    private static final double BREEDING_PROBABILITY = 0.6;
	    // N�mero m�ximo de sardinhas geradas ao se reproduzir
	    private static final int MAX_LITTER_SIZE = 14;
	    // O valor de satisfa��o das algas
	    private static final int SEAWEED_FOOD_VALUE = 10;
	    // Gerador de n�mero rand�mico
	    private static final Random rand = Randomizer.getRandom();


	    /**
	     * Cria uma nova sardinha em algum lugar do oceano
	     * 
	     * @param randomAge Se o peixe deve possuir uma idade aleat�ria
	     * @param field O oceano em que a alga est�.
	     * @param location A localiza��o dentro do oceano.
	     */
	    public Sardine(boolean randomAge, Ocean field, Location location){
	    	super(field, location);
	        setAlive(true);
	        if(randomAge) {
	            setAge(rand.nextInt(MAX_AGE));
	            setFoodLevel(rand.nextInt(SEAWEED_FOOD_VALUE));
	        }
	        else {
	        	setAge(0);
	            setFoodLevel(SEAWEED_FOOD_VALUE);
	        }
	    }
	    
	    /**
	     * Isso � o que a sardinha faz geralmente: ca�a por algas. Nesse
	     * process, se reproduz, morre de fome ou pela idade
		 *
	     * @param newSardine Uma lista para adicionar novas sardinhas 
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
	                setDead();
	            }
	        }
	    }

	   
	    
	    /**
	     * Deixa a sardinha com mais fome, podendo resultar em sua morte
	     */
	    private void incrementHunger()
	    {
	        setFoodLevel(getFoodLevel() - 1);
	        if(getFoodLevel() <= 0) {
	            setDead();
	        }
	    }
	    
	    /**
	     * A sardinha procura por comida em lugares adjacetes a sua posi��o atual.
	     * A primeira alga viva � comida.
	     * 
	     * @param location Onde est� a sardinha no oceano.
	     * @return Onde a comida foi encontrada. Null se n�o foi encontrada.
	     */
	    private Location findFood(Location location)
	    {
	    	Ocean ocean = getField();
			List<Location> adjacent = ocean.adjacentLocations(getLocation());
			Iterator<Location> it = adjacent.iterator();
			while(it.hasNext()) {
				Location where = it.next();
				Object seaweed = ocean.getSeaweedAt(where.getRow(), where.getCol());
				if(seaweed instanceof Seaweed && ocean.getFishAt(where) == null) {
					Seaweed food = (Seaweed) seaweed;
					setFoodLevel(food.getValue());
					food.setIsAlive(false);
					return where;
				}
			}
			return null;
	    }
	    
	    /**
	     * Verifica se a sardinha pode se reproduzir.
	     * Novas sardinha s�o geradas em posi��es adjacentes.
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