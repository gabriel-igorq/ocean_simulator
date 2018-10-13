import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe que representa o oceano da simulação
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public class Ocean
{
	// Gerador de número randômico
    private static final Random rand = Randomizer.getRandom();
    // Altura e largura do oceano
    private int height, width;
    // Matriz para armazenar peixes
    private Fish[][] field;
    // Matriz para armazenar algas. Foi utilizado Object como tipo pois
    // usar a classe Seaweed dificultava a execução da simulação.
    private Object[][] seaweeds;
    
    /**
     * Cria um novo oceano
     * 
     * @param height Altura do oceano
     * @param width Largura do oceano
     */
    public Ocean(int height, int width){
    	this.height = height;
        this.width = width;
        field = new Fish[height][width];
        seaweeds = new Object[height][width];
    }
    
    /**
     * Remove tudo do oceano
     */
    public void clear(){
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
                seaweeds[row][col] = null;
            }
        }
    }
    
    /**
     * Limpa determinada localização do oceano
     * @param location Lugar a ser limpado
     */
    public void clear(Location location){
        field[location.getRow()][location.getCol()] = null;
    }

    /**
     * Posiciona um peixe em determinado lugar no oceano
     * @param fish Peixe a ser posicionado
     * @param row Linha da matriz de peixes
     * @param col Coluna da matriz de peixes
     */
    public void place(Fish fish, int row, int col){
        place(fish, new Location(row, col));
    }
    
    /**
     * Posiciona um peixe em determinada posição do oceano.
     * @param fish Peixe a ser posicionado.
     * @param location Localização onde se deve adicionar o peixe.
     */
    public void place(Fish fish, Location location){
        field[location.getRow()][location.getCol()] = fish;
    }
  
    /**
     * Posiciona uma alga em determinada posição do oceano.
     * @param seaweed Alga a ser posicionada.
     * @param row Linha da matriz de algas.
     * @param col Coluna da matriz de algas.
     */
    public void place(Object seaweed, int row, int col){
    	place(seaweed, new Location(row,col));
    }
    
    /**
     * Posiciona uma alga em determinada posição do oceano.
     * @param seaweed Alga a ser posicionada.
     * @param location Localização onde se deve adicionar a alga
     */
    public void place(Object seaweed, Location location){
    	seaweeds[location.getRow()][location.getCol()] = seaweed;
    }
    
    /**
     * Retorna um peixe em uma determinada posição do oceano.
     * @param location Localização do peixe
     * @return Peixe na localização informada
     */
    public Fish getFishAt(Location location){
        return getFishAt(location.getRow(), location.getCol());
    }
    
    /**
     * Retorna um peixe em uma determinada posição do oceano.
     * @param row Linha da matriz de Peixes
     * @param col Coluna da matriz de Peixes
     * @return Peixe na posição informada
     */
    public Fish getFishAt(int row, int col){
        return field[row][col];
    }
    
    /**
     * Retorna uma alga em determinada localização
     * @param location Localiação dada.
     * @return Alga na posição passada como parâmetro.
     */
    public Object getSeaweedAt(Location location)
    {
        return getSeaweedAt(location.getRow(), location.getCol());
    }
    
    /**
     * Retorna uma alga em determinada posição no oceano.
     * @param row Linha da matriz de objetos de alga.
     * @param col Coluna da matriz de objetos de alga.
     * @return
     */
    public Object getSeaweedAt(int row, int col)
    {
        return seaweeds[row][col];
    }
    
    /**
     * Retorna altura do oceano.
     * @return Altura do oceano.
     */
    public int getHeight(){
        // put something here
        return height;
    }
    
    /**
     * Lista de lugares livres ao redor da localização dada
     * @param location Localização de origem
     * @return Lista de lugares livres
     */
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
    
    /**
     * Verifica localizações livre ao redor da localização dada
     * @param location Localização de origem
     * @return Uma determinada localização livre ou null se não houver nenhuma
     */
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
     * Verifica localizações adjacentes a localização dada
     * 
     * @param location A localização de onde se deve fazer a verificação
     * @return Uma lista de localizações adjacentes
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
    
    /**
     * Retorna a largura do oceano
     * @return width Largura do oceano
     */
    public int getWidth(){
        // and something here
        return width;
    }
    
}
    
