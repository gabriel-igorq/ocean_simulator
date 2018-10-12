
/**
 * Classe abstrata que representa diferentes tipos de peixe.
 * Os peixes se movimentam, comem, procriam e morrem.
 * 
 * @author Gabriel Igor Queiroz Costa, Victor Hugo Freire Ramalho
 * @version 12/10/2018
 */
public abstract class Fish implements Actor{
	
	// Indica se o peixe est� vivo ou n�o
    private boolean alive;
    // Oceano em que o peixe est�.
    private Ocean field;
    // Lugar do oceano em que o peixe est�
    private Location location;
	// Idade do peixe
    private int age;
    // N�vel de satisfa�o do peixe. Aumenta quando ele come
    private int foodLevel;
    
    /**
     * Cria um novo peixe em algum lugar do oceano
     * 
     * @param field O oceano em que o peixe est�.
     * @param location A localiza��o dentro do oceano.
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
     * @return Se o peixe est� vivo ou n�o.
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
     * Se o peixe pode gerar novos peixes ou n�o
     * @return true se ele j� est� na idade de procriar e false caso contr�rio.
     */
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Indica a idade de procria��o de cada tipo de peixe
     */
    abstract public int getBreedingAge();
   
    /**
     * Aumenta a idade do peixe e o mata se ultrapassar a idade m�xima.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Retorna a idade m�xima de cada tipo de peixe.
     */
    abstract public int getMaxAge();
    
    /**
     * Retorna a localiza��o do peixe no oceano.
     * @return Localiza��o do peixe.
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Retorna o oceano em que o peixe est�.
     * @return Oceano em que o peixe vive.
     */
    public Ocean getField(){
        return field;
    }
    
    /**
     * Muda o oceano em que o peixe est�.
     * @param field Novo oceano
     */
    public void setField(Ocean field){
        this.field=field;
    }
    /**
     * Posiciona o peixe em um novo lugar no oceano
     * @param newLocation Nova localiza��o do peixe
     */
    public void setLocation(Location newLocation){
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    
}