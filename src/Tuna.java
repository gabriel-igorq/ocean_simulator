import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
�* Um modelo simples de um atum.
�* Os atuns envelhecem, se movem, se reproduzem e morrem.
�* Os atuns comem sardinhas.
�* @author Gabriel Igor e Victor Hugo
�*/
public class Tuna extends Fish{
	// A idade em que um atum pode come�ar a se reproduzir.
    private static final int BREEDING_AGE = 5;
    // A idade maxima em que um atum pode viver.
    private static final int MAX_AGE = 100;
    // Probabilidade de cria��o de atuns.
    private static final double BREEDING_PROBABILITY = 0.4;
    // O numero maximo de atuns.
    private static final int MAX_LITTER_SIZE = 9;
    // O valor alimentar de uma �nica sardinha. Com efeito, esta � o
    // n�mero de passos que um atum pode ter antes de comer novamente.
    private static final int SARDINE_FOOD_VALUE = 10;
    // Um gerador de n�meros aleat�rios compartilhados para controlar a reprodu��o.
    private static final Random rand = Randomizer.getRandom();


    /**
     * Cria um atum. Um atum pode ser criado como um rec�m nascido (idade zero
�����* e sem fome) ou com uma idade aleat�ria e n�vel de comida.
�����* @param randomAge Se verdadeiro, o atum ter� idade e n�vel de fome aleat�rios.
�����* @param field O campo atualmente ocupado.
�����* @param location A localiza��o dentro do campo.
     */
    public Tuna(boolean randomAge, Ocean field, Location location){
    	super(field, location);
        setAge (0);
        setAlive(true);
        if(randomAge) {
            setAge( rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(SARDINE_FOOD_VALUE));
        }
        else {
            // leave age at 0
            setFoodLevel(SARDINE_FOOD_VALUE);
        }
    }
    
    /**
     * Isto � o que o atum faz a maior parte do tempo: procura sardinhas.
     * No processo, pode se reproduzir, morrer de fome ou morrer de velhice.
�����* @param field O campo atualmente ocupado.
�����* @param newTunas Uma lista para adicionar atuns rec�m-nascidos.
     */
    public void act(List<Actor> newTunas)
    {
    	incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newTunas);            
            // Tenta mover para uma localiza��o livre
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
     * Torne este atum mais faminto. Isso pode resultar na morte do atum.
�����*/
    private void incrementHunger()
    {
    	setFoodLevel(getFoodLevel()-1);
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
    /**
     * Diz ao atum para procurar por sardinhas adjacentes � sua localiza��o atual.
     * S� a primeira sardinha viva � comida.
     * @param location Onde est� localizado no campo
     * @return Onde a comida foi encontrada, ou null, se n�o foi.
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
                    setFoodLevel(SARDINE_FOOD_VALUE);
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Verifique se este atum deve ou ter filho neste passo.
�����* Novos nascimentos ser�o feitos em locais adjacentes livres.
�����* @param newTunas Uma lista para adicionar atuns rec�m-nascidas.
     */
    private void giveBirth(List<Actor> newTunas)
    {
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Tuna young = new Tuna(false, getField(), loc);
            newTunas.add(young);
        }
    }
        
    /**
     * Gera um n�mero representando o n�mero de nascimentos, se pode se reproduzir.
�����* @return O n�mero de nascimentos.
�����*/
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
}
