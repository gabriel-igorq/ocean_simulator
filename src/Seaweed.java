import java.util.List;
import java.util.Random;

/**
 * Uma classe que representa Algas.
 * Como não possui ações, não faz parte da interface Actor.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public class Seaweed {
	
	//Valor de 1 a 10 requisitado para a alga
	private int value;
	//Indica se a alga está viva
	private boolean alive = true;
	//Oceano em que a alga está
	private Ocean field;
	//Posição no oceano da alga
	private Location location;
	//Gera valores randômicos
	private static final Random rand = new Random();
	
    /**
     * Cria uma nova alga em algum lugar do oceano
     * 
     * @param field O oceano em que a alga está.
     * @param location A localização dentro do oceano.
     */
	public Seaweed(Ocean field, Location location){
		this.field = field;
		this.location = location;
		this.value = rand.nextInt(11);
	}
	
	/**
	 * Método de expansão da alga pelo oceano.
	 */
	public void expanding() {
		List<Location> adjacent = field.adjacentLocations(location);
		Location newLocation = new Location(0,0);
        if(adjacent.size() > 0) {
        	newLocation = adjacent.get(0);
        } else {
        	newLocation  = null;
        }
		Seaweed seaweed = new Seaweed(field,newLocation);
		field.place(seaweed, newLocation);
	}
	
	/**
     * @return Se a alga está viva ou não.
     */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * Faz com que a alga viva
	 */
	public void setIsAlive() {
		alive = true;
	}
	
	/**
	 * Faz com que a alga morra
	 */
	public void dead() {
		this.alive = false;
	}
	
	/**
	 * Retorna valor da alga que vai de 1 a 10.
	 * @return valor da alga.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Regenera a alga
	 * @param value Novo valor que terá a alga, variando de 1 a 10
	 */
	public void heal(int value) {
		this.value = value;
	}

}
