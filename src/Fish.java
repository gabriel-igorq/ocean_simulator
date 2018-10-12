
/**
 *Classe baseada em Animal, do projeto fox and rabbits
 */
public abstract class Fish implements Actor{
	
	// Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Ocean field;
    // The animal's position in the field.
    private Location location;
	// The fish's age.
    private int age;
    // The fish's food level, which is increased by eating sardines.
    private int foodLevel;
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(Ocean field, Location location){
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
	public int getFoodLevel() {
		return foodLevel;
	}
	
	public void setFoodLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
    public boolean isAlive(){
        return alive;
    }
    
    public void setAlive(boolean a){
        alive=a;
    }
	
    public void setDead(){
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
	
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    abstract public int getBreedingAge();
   
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    abstract public int getMaxAge();
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Ocean getField(){
        return field;
    }
    public void setField(Ocean field){
        this.field=field;
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation){
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    public Ocean getOcean() {
    	return field;
    }
    
}