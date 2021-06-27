package RestaurantSim;

/**
 * Class that resembles an edible ingredient.
 * It is an important building block for {@link Dish}.
 */
public class Ingredient
{
    private final float price;
    private final int ticksToPrepare;
    private final String name;

    /**
     * Creates a new Ingredient
     * @param name Name of the new ingredient
     * @param price Price of the new ingredient
     * @param ticksToPrepare Amount of ticks that it takes to prepare this ingredient
     */
    public Ingredient(String name, float price, int ticksToPrepare){
        this.price=price;
        this.name=name;
        this.ticksToPrepare = ticksToPrepare;
    }

    /**
     * Returns a price of this ingredient
     * @return Float containing price of this ingredient
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * Returns name of this ingredient
     * @return String with name of ingredient
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns amount of ticks that it takes to prepare this ingredient
     * @return Integer of value of total ticks that it takes to prepare this ingredient
     */
    public int getTicksToPrepare()
    {
        return ticksToPrepare;
    }

    @Override
    public String toString() {
        return "Ingredient{" + ", name='" + name + '}';
    }
}
