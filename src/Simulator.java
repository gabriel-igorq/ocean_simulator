import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Simulador de oceano contendo sardinhas, atums e tubarões.
 * Algas não são visíveis, mas também fazem parte do ecossistema.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */

public class Simulator{
	//Oceano do simulador
    private Ocean ocean;
    //Etapas da simulação
    private int step;
    //Maneira de visuaizar a simulação
    private SimulatorView simView;

    // Lista de animais
    private List<Actor> animals;
    // Probabilidades de gerar cada tipo de peixe
    private static final double SARDINE_CREATION_PROBABILITY = 0.035;    
    private static final double TUNAS_CREATION_PROBABILITY = 0.06; 
    private static final double SHARK_CREATION_PROBABILITY = 0.09;
   
    /**
    * Função principal
    */
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 100);
        sim.simulate(1000);
    }
    
    /**
     * Construtor do simulador
     * @param height altura da representação do oceano
     * @param width largura da representação do oceano
     */
    public Simulator(int height, int width){

    	animals = new ArrayList<Actor>();
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
       
        
        // define in which color fish should be shown
        simView.setColor(Sardine.class, Color.red);
        simView.setColor(Tuna.class, Color.blue);
        simView.setColor(Shark.class, Color.black);
        //simView.setColor(Seaweed.class, Color.green);
        reset();
    }
    
    /**
     * Realiza apenas uma etapa da simulação.
     * Cada actor realiza uma ação e as algas se expandem. 
     */
    public void simulateOneStep(){
        step++;
        
        // Provide space for newborn animals.
        List<Actor> newAnimals = new ArrayList<Actor>(); 
           
        // Let all rabbits act.
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()){
                it.remove();
            }
        }
        animals.addAll(newAnimals);
        simView.showStatus(step, ocean);   
    }
    
    /**
     * Realiza a simulação
     * @param numSteps Número de passos da simulação
     */
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && simView.isViable(ocean); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Reseta a simulação
     */
    public void reset(){
        step = 0;
        animals.clear();
        populate();
        simView.showStatus(step, ocean);
    }
    
    /**
     * Função para preencher o oceano com peixes.
     */
    private void populate(){
    	Random rand = Randomizer.getRandom();
        ocean.clear();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
            	if(rand.nextDouble() <= SARDINE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sardine sardine = new Sardine(true, ocean, location);
                    animals.add(sardine);
                } else if(rand.nextDouble() <= TUNAS_CREATION_PROBABILITY) {
                     Location location = new Location(row, col);
                     Tuna tuna = new Tuna(true, ocean, location);
                     animals.add(tuna);
                 }
                 else if(rand.nextDouble() <= SHARK_CREATION_PROBABILITY) {
                	 Location location = new Location(row, col);
                	 Shark shark = new Shark(true, ocean, location);
                	 animals.add(shark);
                 }
            }
        }
    }
    
    /**
     * Executa determinada quantidade de passos da simulação.
     * @param steps Número de passos.
     */
    public void run(int steps)
    {
        simView.showStatus(0, ocean);
    }
}
