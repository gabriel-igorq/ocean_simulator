import java.util.List;

/**
 * Uma interface contendo todas as classes que possuem um método act
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */

public interface Actor {

	/**
	 * Ações comuns dos atores
	 * @param newActors Uma lista para armazenar os atores.
	 */
	void act(List <Actor> newActors);
	
	/**
	 * @return true se o ator está vivo e falso se não estiver.
	 */
	boolean isAlive();
}
