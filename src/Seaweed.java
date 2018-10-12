import java.util.Random;

/**
 * 
 * @author Gabriel Igor e Victor Hugo
 * @version 12/10/2018
 */
public class Seaweed {
	
	private int value;
	private boolean alive = true;
	private Ocean field;
	private Location location;
	private static final Random rand = new Random();
	
	public Seaweed(Ocean field, Location location){
		this.field = field;
		this.location = location;
		this.value = rand.nextInt(11);
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setIsAlive() {
		alive = true;
	}
	
	
	public void dead() {
		this.alive = false;
	}
	

	public int getValue() {
		return this.value;
	}
	
	public void heal(int value) {
		this.value = value;
	}

	public void expanding() {
		Seaweed seaweed = new Seaweed(field,field.random(location));
		field.place(seaweed,field.random(location));
	}
}
