
/**
 *Classe baseada em Animal, do projeto fox and rabbits
 */
public abstract class Fish extends Cell{
	// The tuna's age.
    private int age;
    // The tuna's food level, which is increased by eating sardines.
    private int foodLevel;
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(Ocean field, Location location){
    	super(field,location);
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
	
	/*public void setDead()
    {
        setAlive (false);
        if(getLocation() != null) {
            getField().clear(getLocation());
            setLocation(null);
            setField(null);
        }
    }*/
	
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
    
}