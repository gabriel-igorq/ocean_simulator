import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Um modelo simples de um tubarão.
 * Os tubarõess envelhecem, se movem, se reproduzem e morrem.
 * Os tubarões comem atuns ou sardinhas, mas preferem atuns.
 * Os tubarõess são solitários - eles preferem não nadar um ao lado do outro
 * @author Gabriel Igor Queiroz Costa e Victor Hugo Freire Ramalho
 */
public class Shark extends Fish
{
	// A idade em que um tubarão pode começar a se reproduzir.
    private static final int BREEDING_AGE = 11;
    // A idade maxima em que um tubarão pode viver.
    private static final int MAX_AGE = 130;
    // Probabilidade de criação de tubarÃµes.
    private static final double BREEDING_PROBABILITY = 0.45;
    // O numero maximo de aniversarios.
    private static final int MAX_LITTER_SIZE = 1;
    // O valor alimentar de um único atum ou sardinha. Com efeito, isto é
    // número de passos que um tubarão pode ter antes de comer novamente.
    private static final int TUNA_FOOD_VALUE = 8;
    private static final int SARDINE_FOOD_VALUE = 5;
    // Um gerador de números aleatórios compartilhados para controlar a reprodução.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Cria um tubarão. Um tubarão pode ser criado como um recém nascido (idade zero
     * e sem fome) ou com uma idade aleatória e nível de comida.
     * @param randomAge Se verdadeiro, o tubarão terá idade e nível de fome aleatórios.
     * @param field O campo atualmente ocupado.
     * @param location A localização dentro do campo.
     */
    public Shark(boolean randomAge, Ocean field, Location location)
    {
        super(field, location);
        setAge (0);
        setAlive(true);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(TUNA_FOOD_VALUE));
        }
        else {
        	setAge(0);
        	setFoodLevel(TUNA_FOOD_VALUE);
        }
    }
    
    /**
     * Representa o que os tubarões fazem a maior parte do tempo: eles caçam
     * comida. No processo, pode se reproduzir, morrer de fome,
     * ou morrer de velhice.
     * @param field O campo atualmente ocupado.
     * @param newSharks Uma lista para adicionar tubarões recém-nascidos.
     */
    public void act(List<Actor> newSharks)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSharks);            
            // Move-se em direção a uma fonte de comida, se encontrada.
            Location location = getLocation();
            Location newLocation = findFood(getLocation());
            if(newLocation == null) { 
            	newLocation = moveAway(location);
                if(newLocation == null) {
                	newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            // Vê se é possivel se mover
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }

    /**
     * Torne este tubarão mais faminto. Isso pode resultar na morte do tubarão.
     */
    private void incrementHunger()
    {
    	setFoodLevel(getFoodLevel()-1);
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
    /**
     * Diz aos tubarões para procurar atuns adjacentes à  sua localização atual.
     * Apenas o primeiro atum vivo ou sardinha é comido.
     * @param location Onde no campo está localizado.
     * @return Onde a comida foi encontrada, ou null, se não foi.
     */
    private Location findFood(Location location)
    {
        List<Location> adjacent = getField().adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Fish animal = getField().getFishAt(where);
            if(animal instanceof Tuna) {
                Tuna tuna = (Tuna) animal;
                if(tuna.isAlive()) { 
                    tuna.setDead();
                    setFoodLevel(TUNA_FOOD_VALUE);
                    return where;
                }
            } else if(animal instanceof Sardine) {
                Sardine sardine = (Sardine) animal;
                if(sardine.isAlive()) { 
                    sardine.setDead();
                    setFoodLevel(SARDINE_FOOD_VALUE);
                    return where;
                }
            } 
        }
        return null;
    }
    
    /**
     * Verifique se este tubarão deve ou ter filhos neste passo.
     * Novos nascimentos serão feitos em locais adjacentes livres.
     * @param newSharks Uma lista para adicionar tubarões recém-nascidos.
     */
    private void giveBirth(List<Actor> newSharks)
    {
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Shark young = new Shark(false, getField(), loc);
            newSharks.add(young);
        }
    }
        
    /**
     * Gera um número representando o número de nascimentos, se pode se reproduzir.
     * @return O número de nascimentos.
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
     * Retorna a idade de reprodução
     * @return a idade de reprodução
     */
    public int getBreedingAge() {
    	return BREEDING_AGE;
    }
    /**
     * Retorna a idade maxima
     * @return a idade maxima
     */
    public int getMaxAge() {
    	return MAX_AGE;
    }
    /**
     * Faz o tubarão morrer, retirando-o do oceano.
     */
    public void leaveOcean() {
    	setDead();
    }
    /**
     * Faz o tubarão se mover. Se caso ache um tubarão proximo, deve sair de perto.
     * @param location Onde está localizado no campo
     * @return Se foi encontrado tubarão, ou null, se não foi.
     */
    private Location moveAway(Location location){
    	Ocean ocean = getField();
    	List<Location> adjacents = ocean.adjacentLocations(location);
    	for(Location newLocation : adjacents) {
    		Object obj = ocean.getFishAt(newLocation);
    		if(obj instanceof Shark) {
    			Shark shark = (Shark) obj;
    			shark.leaveOcean();
        		return newLocation;
    		}
    	}
    	return null;
    }
}
