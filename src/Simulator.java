import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * (Fill in description and author info here)
 */

public class Simulator{
    private Ocean ocean;
    private int step;
    private SimulatorView simView;
    private List<Sardine> sardines;
    private List<Tuna> tunas;
    private double rands;
    private static final double SARDINE_CREATION_PROBABILITY = 0.07;    
    private static final double TUNAS_CREATION_PROBABILITY = 0.05; 
    public static void main(String[] args) {
        Simulator sim = new Simulator(50, 100);
        sim.simulate(100);
    }
       
    public Simulator(int height, int width){
    	sardines = new ArrayList<Sardine>();
    	tunas = new ArrayList<Tuna>();
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
       
        
        // define in which color fish should be shown
        simView.setColor(Sardine.class, Color.red);
        simView.setColor(Tuna.class, Color.blue);
        reset();
    }
    public void simulateOneStep(){
        step++;
        // Provide space for newborn sardines.
        List<Fish> newSardines = new ArrayList<Fish>(); 
        List<Sardine> newSardines2 = new ArrayList<Sardine>(); 
        
        // Let all sardines act.
        for(Iterator<Sardine> it = sardines.iterator(); it.hasNext(); ) {
            Sardine sardine = it.next();
            sardine.act(newSardines);
            if(!sardine.isAlive()) 
                it.remove();
           //  else 
            //	sardines.add(sardine);  
        }
        for(Iterator<Fish> it = newSardines.iterator(); it.hasNext(); ) {
        	Fish fish=it.next();
        	newSardines2.add((Sardine) fish);
        }
        sardines.addAll(newSardines2);
       // for(Iterator<Sardine> it = sardines.iterator(); it.hasNext(); ) {
      //      Sardine sardine = it.next();
      //      sardines.add(sardine);    
      //  }
        
        // Provide space for newborn tunas.
        List<Fish> newTunas = new ArrayList<Fish>();   
        List<Tuna> newTunas2 = new ArrayList<Tuna>();
        // Let all tunas act.
        for(Iterator<Tuna> it = tunas.iterator(); it.hasNext(); ) {
            Tuna tuna = it.next();
            tuna.act(newTunas);
            if(! tuna.isAlive()) 
                it.remove();
            //else 
           // 	tunas.add(tuna);
        }
        for(Iterator<Fish> it = newTunas.iterator(); it.hasNext(); ) {
        	Fish fish=it.next();
        	newTunas2.add((Tuna) fish);
        }
        tunas.addAll(newTunas2);
        
        // Add the newly born foxes and rabbits to the main lists.
       // sardines.addAll( newRabbits);
       // tunas.addAll( newFoxes);

        simView.showStatus(step, ocean);
    }
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && simView.isViable(ocean); step++) {
            simulateOneStep();
        }
    }
    public void reset(){
        step = 0;
        sardines.clear();
        tunas.clear();
//        foxes.clear();
        populate();
        
        // Show the starting state in the view.
        simView.showStatus(step, ocean);
    }
    private void populate(){
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
            	rands = rand.nextDouble();
                /*if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, ocean, location);
                    foxes.add(fox);
                }*/
                 if(rands <= SARDINE_CREATION_PROBABILITY && rands >= TUNAS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sardine sardine = new Sardine(true, ocean, location);
                    sardines.add(sardine);
                }
                 else if(rands <= TUNAS_CREATION_PROBABILITY && rands >=0.03 ) {
                     Location location = new Location(row, col);
                     Tuna tuna = new Tuna(true, ocean, location);
                     tunas.add(tuna);
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
