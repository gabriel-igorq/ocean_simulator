import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * (Fill in description and author info here)
 */

public class Simulator
{
    private Ocean ocean;
    private int step;
    private SimulatorView simView;
    private List<Sardine> sardines;
    private static final double SARDINE_CREATION_PROBABILITY = 0.08;    
    public static void main(String[] args) 
    {
        Simulator sim = new Simulator(50, 100);
        sim.run(100);
    }
    
    
    
    public Simulator(int height, int width)
    {
    	sardines = new ArrayList<Sardine>();
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
       
        
        // define in which color fish should be shown
        simView.setColor(Sardine.class, Color.red);
        reset();
    }
    public void reset()
    {
        step = 0;
        sardines.clear();
//        foxes.clear();
        populate();
        
        // Show the starting state in the view.
        simView.showStatus(step, ocean);
    }
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                /*if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, ocean, location);
                    foxes.add(fox);
                }*/
                 if(rand.nextDouble() <= SARDINE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sardine sardine = new Sardine(true, ocean, location);
                    sardines.add(sardine);
                }
                // else leave the location empty.
            }
        }
    }
    
    public void run(int steps)
    {
        // put the simulation main loop here
        
        simView.showStatus(0, ocean);
    }
}
