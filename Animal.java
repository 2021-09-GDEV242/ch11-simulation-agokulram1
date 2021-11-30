import java.util.List;
import java.util.Random;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    private static final int BREEDING_AGE = 0;
    // The age to which a fox can live.
    private static final int MAX_AGE = 0;
    // The likelihood of a fox breeding.
    private static final Random rand = Randomizer.getRandom();
     private int age;
    // The animal's food level, which is increased as time goes on.
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Returns the breeding age of this animal
     * @return the breeding age of this animal
     */
    protected abstract int getBreedingAge();
    
    /**
     * Increase the age. This could result in the Animal's death.
     * @return the incrementAge for Animal 
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * age for superclass Animal
     * @return the max age of the animal (fox or rabbit)
     */
    protected abstract int getMaxAge();
    
     /**
     * This method does the breeding for the animals 
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProb()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    /**
     * This method does the getBreedingProb for animals.
     * @return The getBreedingProb for animals
     */
    protected abstract double getBreedingProb();
    /**
     * This method does the getMaxLitterSize for animals.
     * @return the getMaxLitterSize for animals.
     */
    protected abstract int getMaxLitterSize();
}
