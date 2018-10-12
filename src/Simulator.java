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
    /*
    private List<Seaweed> seaweeds;
    private List<Sardine> sardines;
    private List<Tuna> tunas;
    private List<Shark> sharks;
    */
    // List of animals in the field.
    private List<Actor> animals;
    private double rands;
    private static final double SARDINE_CREATION_PROBABILITY = 0.07;    
    private static final double TUNAS_CREATION_PROBABILITY = 0.05; 
    private static final double SHARK_CREATION_PROBABILITY = 0.03;
    private static final double SEAWEED_CREATION_PROBABILITY = 0.1;
    public static void main(String[] args) {
        Simulator sim = new Simulator(50, 100);
        sim.simulate(50);
    }
       
    public Simulator(int height, int width){
    	/*
    	sardines = new ArrayList<Sardine>();
    	seaweeds = new ArrayList<Seaweed>();
    	tunas = new ArrayList<Tuna>();
    	sharks = new ArrayList<Shark>();
    	*/
    	animals = new ArrayList<Actor>();
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
       
        
        // define in which color fish should be shown
        simView.setColor(Sardine.class, Color.red);
        simView.setColor(Tuna.class, Color.blue);
        simView.setColor(Shark.class, Color.black);
        simView.setColor(Seaweed.class, Color.green);
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
        /*
        // Provide space for newborn animals.
        List<Cell> newAnimals = new ArrayList<Cell>(); 
           
        // Let all rabbits act.
        for(Iterator<Cell> it = animals.iterator(); it.hasNext(); ) {
            Cell animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        */
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);

        simView.showStatus(step, ocean);
        
        /*
        List<Seaweed> newSeaweeds2 = new ArrayList<Seaweed>(); 
        for(Iterator<Seaweed> it = seaweeds.iterator(); it.hasNext(); ) {
            Seaweed seaweed = it.next();
            seaweed.act(newSeaweeds);
            if(!seaweed.isAlive()) 
                it.remove(); 
        }
        for(Iterator<Cell> it = newSeaweeds.iterator(); it.hasNext(); ) {
        	Cell fish=it.next();
        	newSeaweeds2.add((Seaweed) fish);
        }
        seaweeds.addAll(newSeaweeds2);
////////////////////////////////////////////////////////////////////////
        List<Cell> newSardines = new ArrayList<Cell>();   
        List<Sardine> newSardines2 = new ArrayList<Sardine>();
        for(Iterator<Sardine> it = sardines.iterator(); it.hasNext(); ) {
            Sardine sardine = it.next();
            sardine.act(newSardines);
            if(!sardine.isAlive()) 
                it.remove();
        }
        for(Iterator<Cell> it = newSardines.iterator(); it.hasNext(); ) {
        	Cell fish=it.next();
        	newSardines2.add((Sardine) fish);
        }
        sardines.addAll(newSardines2);
 //////////////// ///////////////////////////////////////////////////////
        List<Cell> newTunas = new ArrayList<Cell>();   
        List<Tuna> newTunas2 = new ArrayList<Tuna>();
        for(Iterator<Tuna> it = tunas.iterator(); it.hasNext(); ) {
            Tuna tuna = it.next();
            tuna.act(newTunas);
            if(! tuna.isAlive()) 
                it.remove();
        }
        for(Iterator<Cell> it = newTunas.iterator(); it.hasNext(); ) {
        	Cell fish=it.next();
        	newTunas2.add((Tuna) fish);
        }
        tunas.addAll(newTunas2);
//////////////////////////////////////////////////////////////////////////
        List<Cell> newShark = new ArrayList<Cell>();   
        List<Shark> newShark2 = new ArrayList<Shark>();
        for(Iterator<Shark> it = sharks.iterator(); it.hasNext(); ) {
            Shark shark = it.next();
            shark.act(newShark);
            if(! shark.isAlive()) 
                it.remove();
        }
        for(Iterator<Cell> it = newShark.iterator(); it.hasNext(); ) {
        	Cell fish=it.next();
        	newShark2.add((Shark) fish);
        }
        sharks.addAll(newShark2);
        */
        
        //simView.showStatus(step, ocean);
    }
    
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && simView.isViable(ocean); step++) {
            simulateOneStep();
        }
    }
    
    public void reset(){
        step = 0;
        /*
        seaweeds.clear();
        sardines.clear();
        tunas.clear();
        sharks.clear();
        */
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
            	rands = rand.nextDouble();
            	if(rands <= SEAWEED_CREATION_PROBABILITY && rands > SARDINE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Seaweed seaweed = new Seaweed(true, ocean, location);
                    animals.add(seaweed);
                }
            	else if(rands <= SARDINE_CREATION_PROBABILITY && rands > TUNAS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sardine sardine = new Sardine(true, ocean, location);
                    animals.add(sardine);
                }
                 else if(rands <= TUNAS_CREATION_PROBABILITY && rands >SHARK_CREATION_PROBABILITY ) {
                     Location location = new Location(row, col);
                     Tuna tuna = new Tuna(true, ocean, location);
                     animals.add(tuna);
                 }
                 else if(rands <= SHARK_CREATION_PROBABILITY) {
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
