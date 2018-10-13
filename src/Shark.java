import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
�* Um modelo simples de um tubar�o.
�* Os tubar�ess envelhecem, se movem, se reproduzem e morrem.
�* Os tubar�es comem atuns ou sardinhas, mas preferem atuns.
�* Os tubar�ess s�o solit�rios - eles preferem n�o nadar um ao lado do outro
�* @author Gabriel Igor Queiroz Costa e Victor Hugo Freire Ramalho
�*/
public class Shark extends Fish
{
	// A idade em que um tubar�o pode come�ar a se reproduzir.
    private static final int BREEDING_AGE = 11;
    // A idade maxima em que um tubar�o pode viver.
    private static final int MAX_AGE = 130;
    // Probabilidade de cria��o de tubarões.
    private static final double BREEDING_PROBABILITY = 0.45;
    // O numero maximo de aniversarios.
    private static final int MAX_LITTER_SIZE = 1;
    // O valor alimentar de um �nico atum ou sardinha. Com efeito, isto �
    // n�mero de passos que um tubar�o pode ter antes de comer novamente.
    private static final int TUNA_FOOD_VALUE = 8;
    private static final int SARDINE_FOOD_VALUE = 5;
    // Um gerador de n�meros aleat�rios compartilhados para controlar a reprodu��o.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Cria um tubar�o. Um tubar�o pode ser criado como um rec�m nascido (idade zero
    �* e sem fome) ou com uma idade aleat�ria e n�vel de comida.
    �* @param randomAge Se verdadeiro, o tubar�o ter� idade e n�vel de fome aleat�rios.
    �* @param field O campo atualmente ocupado.
    �* @param location A localiza��o dentro do campo.
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
     * Representa o que os tubar�es fazem a maior parte do tempo: eles ca�am
     * comida. No processo, pode se reproduzir, morrer de fome,
   � * ou morrer de velhice.
  �  * @param field O campo atualmente ocupado.
   � * @param newSharks Uma lista para adicionar tubar�es rec�m-nascidos.
     */
    public void act(List<Actor> newSharks)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSharks);            
            // Move-se em dire��o a uma fonte de comida, se encontrada.
            Location location = getLocation();
            Location newLocation = findFood(getLocation());
            if(newLocation == null) { 
            	newLocation = moveAway(location);
                if(newLocation == null) {
                	newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            // V� se � possivel se mover
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }

    /**
     * Torne este tubar�o mais faminto. Isso pode resultar na morte do tubar�o.
    �*/
    private void incrementHunger()
    {
    	setFoodLevel(getFoodLevel()-1);
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
    /**
     * Diz aos tubar�es para procurar atuns adjacentes � sua localiza��o atual.
    �* Apenas o primeiro atum vivo ou sardinha � comido.
    �* @param location Onde no campo est� localizado.
    �* @return Onde a comida foi encontrada, ou null, se n�o foi.
    �*/
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
     * Verifique se este tubar�o deve ou ter filhos neste passo.
    �* Novos nascimentos ser�o feitos em locais adjacentes livres.
    �* @param newSharks Uma lista para adicionar tubar�es rec�m-nascidos.
    �*/
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
     * Gera um n�mero representando o n�mero de nascimentos, se pode se reproduzir.
�    * @return O n�mero de nascimentos.
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
     * Retorna a idade de reprodu��o
     * @return a idade de reprodu��o
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
     * Faz o tubar�o morrer, retirando-o do oceano.
     */
    public void leaveOcean() {
    	setDead();
    }
    /**
     * Faz o tubar�o se mover. Se caso ache um tubar�o proximo, deve sair de perto.
     * @param location Onde est� localizado no campo
     * @return Se foi encontrado tubar�o, ou null, se n�o foi.
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
