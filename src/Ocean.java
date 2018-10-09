import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Ocean
{
	 // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int height, width;
    // Storage for the animals.
    private Object[][] field;
    
    
    public Ocean(int height, int width){
    	this.height = height;
        this.width = width;
        field = new Object[height][width];
    }
    
    public void clear(){
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    public void clear(Location location){
        field[location.getRow()][location.getCol()] = null;
    }
    
    public void place(Object animal, int row, int col){
        place(animal, new Location(row, col));
    }
    public void place(Object animal, Location location){
        field[location.getRow()][location.getCol()] = animal;
    }
    public Object getObjectAt(Location location){
        return getObjectAt(location.getRow(), location.getCol());
    }
    public Object getObjectAt(int row, int col){
        return field[row][col];
    }
    public int getHeight(){
        // put something here
        return height;
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
}
