import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Wolf here.
 *
 * @author Gokul Ram
 * @version 2021.11.29 (2)
 */
public class Wolf extends Animal
{

     // Characteristics shared by all  (class variables).
    
    // The age at which a Wolf can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a Wolf can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a Wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a Wolf can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The Wolf's age.
    private int age;
    // The Wolf's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a Wolf. A Wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Wolf will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the Wolf does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param new Wolves A list to return newly born Wolves.
     */
    public void act(List<Animal> newWolves)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newWolves);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

  /**
     * Increase the age. This could result in the Wolf's death.
     *//*
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }*/
    
    /**
     * Make this Wolf more hungry. This could result in the Wolf's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this Wolf is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newWolves A list to return newly born Wolves.
     */
    private void giveBirth(List<Animal> newWolves)
    {
        // New Wolves are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newWolves.add(young);
        }
    }
        
   /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    /*private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }*/

    /**
     * A Wolf can breed if it has reached the breeding age.
     * @return true if the Wolf can breed, false otherwise.
     */
    /*private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }*/
    /**
     * this is a mutator method for Wolf
     */
    public void setAge(int newage)
    {
    if ( newage > MAX_AGE)
    age = MAX_AGE;
    else 
    age = newage;
    }
    
    /**
     * this is an accesor method for Wolf
     * @return the age of the Wolf
     */
    private int getAge()
    {
    return age;
    }
    /**
     * This is the concrete method for getBreedingAge abstract method from Animal Class.
     * @return The age at which a Wolf starts to breed
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * This is the concrete method for getMaxAge abstract method from Animal Class.
     * @return The max age of the Wolf 
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * This is the concrete method for getBreedingProb abstract method from Animal Class.
     * @return The getBreedingProb of the Wolf.
     */
    protected  double getBreedingProb() 
    {
     return BREEDING_PROBABILITY;
    }
      /**
       * This is the concrete method for getMaxLitterSize abstract method from Animal Class.
       * @return The getMaxLitterSize of the Wolf.
       */  
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
}
