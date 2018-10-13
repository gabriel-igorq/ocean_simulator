import java.util.List;
import java.util.Random;

/**
 * Uma classe que representa Algas.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public class Seaweed implements Actor {
	
	//Valor de 1 a 10 requisitado para a alga
	private int value;
	//Indica se a alga est� viva
	private boolean alive = true;
	//Oceano em que a alga est�
	private Ocean field;
	//Posi��o no oceano da alga
	private Location location;
	//Gera valores rand�micos
	private static final Random rand = new Random();
	
    /**
     * Cria uma nova alga em algum lugar do oceano
     * 
     * @param field O oceano em que a alga est�.
     * @param location A localiza��o dentro do oceano.
     */
	public Seaweed(Ocean field, Location location){
		this.field = field;
		this.location = location;
		this.value = rand.nextInt(11);
	}
	
	/**
	 * M�todo de a��o da alga. Verifica se o valor da alga � maior que 5, caso seja
	 * a alga ir� se expandir.
	 */
	public void act(List<Actor> newSeaweeds) {
		this.value = value;
		for(int row = 0; row < field.getHeight(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Seaweed seaweed = (Seaweed) field.getSeaweedAt(location);
                if(seaweed != null) {
                	//verifica se o valor � maior que 5 para se expandir
            		if(value > 5) {
            			List<Location> adjacent = field.adjacentLocations(location);
            			Location newLocation = new Location(0,0);
            	        if(adjacent.size() > 0) {
            	        	newLocation = adjacent.get(0);
            	        } else {
            	        	newLocation  = null;
            	        }
            			Seaweed newSeaweed = new Seaweed(field,newLocation);
            			field.place(newSeaweed, newLocation);
            		}
                }
            }
    	}
		
	}
	
	/**
     * @return Se a alga est� viva ou n�o.
     */
	public boolean isAlive() {
		return this.alive;
	}
	
	
	/**
	 * Faz com que a alga viva
	 */
	public void setIsAlive(boolean life) {
		alive = life;
	}	
	
	/**
	 * Retorna valor da alga que vai de 1 a 10.
	 * @return valor da alga.
	 */
	public int getValue() {
		return this.value;
	}
	

}
