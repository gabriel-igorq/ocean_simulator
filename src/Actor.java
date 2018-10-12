import java.util.List;

/**
 * Uma interface contendo todas as classes que possuem um m�todo act
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */

public interface Actor {

	/**
	 * A��es comuns dos atores
	 * @param newActors Uma lista para armazenar os atores.
	 */
	void act(List <Actor> newActors);
	
	/**
	 * @return true se o ator est� vivo e falso se n�o estiver.
	 */
	boolean isAlive();
}
