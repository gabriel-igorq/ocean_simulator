
/**
 * Classe abstrata que representa diferentes tipos de peixe.
 * Os peixes se movimentam, comem, procriam e morrem.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public abstract class Fish implements Actor{
	
	// Indica se o peixe está vivo ou não
    private boolean alive;
    // Oceano em que o peixe está.
    private Ocean field;
    // Lugar do oceano em que o peixe está
    private Location location;
	// Idade do peixe
    private int age;
    // Nível de satisfaão do peixe. Aumenta quando ele come
    private int foodLevel;
    
    /**
     * Cria um novo peixe em algum lugar do oceano
     * 
     * @param field O oceano em que o peixe está.
     * @param location A localização dentro do oceano.
     */
    public Fish(Ocean field, Location location){
        alive = true;
        this.field = field;
        setLocation(location);
    }
    /**
     * @return Foodlevel do peixe.
     */
	public int getFoodLevel() {
		return foodLevel;
	}
	
	/**
	 * Muda o foodlevel do peixe.
     * @param foodlevel Novo foodlevel.
     */
	public void setFoodLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}
	
	/**
     * @return Idade do peixe.
     */
	public int getAge() {
		return age;
	}
	
	/**
	 * Muda a idade do peixe.
     * @param age Nova idade
     */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
     * @return Se o peixe está vivo ou não.
     */
    public boolean isAlive(){
        return alive;
    }
    
	/**
     * @param a Novo estado do peixe.
     */
    public void setAlive(boolean a){
        alive = a;
    }
	
    /**
     * Mata o peixe e o remove do oceano.
     */
    public void setDead(){
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
	
    /**
     * Se o peixe pode gerar novos peixes ou não
     * @return true se ele já está na idade de procriar e false caso contrário.
     */
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Indica a idade de procriação de cada tipo de peixe
     */
    abstract public int getBreedingAge();
   
    /**
     * Aumenta a idade do peixe e o mata se ultrapassar a idade máxima.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Retorna a idade máxima de cada tipo de peixe.
     */
    abstract public int getMaxAge();
    
    /**
     * Retorna a localização do peixe no oceano.
     * @return Localização do peixe.
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Retorna o oceano em que o peixe está.
     * @return Oceano em que o peixe vive.
     */
    public Ocean getField(){
        return field;
    }
    
    /**
     * Muda o oceano em que o peixe está.
     * @param field Novo oceano
     */
    public void setField(Ocean field){
        this.field=field;
    }
    /**
     * Posiciona o peixe em um novo lugar no oceano
     * @param newLocation Nova localização do peixe
     */
    public void setLocation(Location newLocation){
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    
}