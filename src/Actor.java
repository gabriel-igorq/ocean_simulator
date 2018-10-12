import java.util.List;

public interface Actor {

	/**
	 * Executes regular actions from actor.
	 * @param newActors A list to storage new actors created.
	 */
	
	void act(List <Actor> newActors);
	
	boolean isAlive();
}
