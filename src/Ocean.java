import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Gabriel Igor e Victor Hugo
 *
 */
public class Ocean
{
	 // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int height, width;
    // Storage for the animals.
    private Fish[][] field;
    private Object[][] seaweeds;
    //private Seaweed[][] seaweed;
    
    
    public Ocean(int height, int width){
    	this.height = height;
        this.width = width;
        field = new Fish[height][width];
        seaweeds = new Object[height][width];
    }
    
    public void clear(){
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
                seaweeds[row][col] = null;
            }
        }
    }
    public void clear(Location location){
        field[location.getRow()][location.getCol()] = null;
    }

    
    public void place(Fish fish, int row, int col){
        place(fish, new Location(row, col));
    }
    public void place(Fish fish, Location location){
        field[location.getRow()][location.getCol()] = fish;
    }
  
    public void place(Object seaweed, int row, int col){
    	place(seaweed, new Location(row,col));
    }
    
    public void place(Object seaweed, Location location){
    	seaweeds[location.getRow()][location.getCol()] = seaweed;
    }
    
    
    public Fish getFishAt(Location location){
        return getFishAt(location.getRow(), location.getCol());
    }
    public Fish getFishAt(int row, int col){
        return field[row][col];
    }
    
    public Object getSeaweedAt(Location location)
    {
        return getSeaweedAt(location.getRow(), location.getCol());
    }
    
    public Object getSeaweedAt(int row, int col)
    {
        return seaweeds[row][col];
    }
    
    public int getHeight(){
        // put something here
        return height;
    }
    public List<Location> getFreeAdjacentLocations(Location location){
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getFishAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    public Location freeAdjacentLocation(Location location){
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }
    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location){
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < height) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
    public int getWidth(){
        // and something here
        return width;
    }
    
    public void seaweedExpansion() {
    	Random rand = new Random();
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                Location location = new Location(row, col);
                Seaweed seaweed = (Seaweed) getSeaweedAt(location);
                if(seaweed != null) {
                    seaweed.heal(rand.nextInt(11));
                    seaweed.expanding();
                    seaweed.setIsAlive();
                }
            }
    	}
    }
    
    public Location random(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        if(adjacent.size() > 0) {
        	return adjacent.get(0);
        } else {
        	return null;
        }
    }
}