import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * @author Gabriel Igor e Victor Hugo
 */

public class Simulator{
    private Ocean ocean;
    private int step;
    private SimulatorView simView;

    // List of animals in the field.
    private List<Actor> animals;
   // private double rands;
    private static final double SARDINE_CREATION_PROBABILITY = 0.035;    
    private static final double TUNAS_CREATION_PROBABILITY = 0.06; 
    private static final double SHARK_CREATION_PROBABILITY = 0.09;
   
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 100);
        sim.simulate(1000);
    }
       
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
    public void simulateOneStep(){
        step++;
        
        // Provide space for newborn animals.
        List<Actor> newAnimals = new ArrayList<Actor>(); 
           
        // Let all rabbits act.
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        ocean.seaweedExpansion();
        animals.addAll(newAnimals);
        simView.showStatus(step, ocean);   
    }
    
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && simView.isViable(ocean); step++) {
            simulateOneStep();
        }
    }
    
    public void reset(){
        step = 0;
       
        animals.clear();
        populate();
    
        // Show the starting state in the view.
        simView.showStatus(step, ocean);
    }
    
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
                // else leave the location empty.
            }
        }
    }
    
    public void run(int steps)
    {
        simView.showStatus(0, ocean);
    }
}
