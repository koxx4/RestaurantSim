package RestaurantSim;

public class Ingredient
{
    private final float price;
    private final int ticksToPrepare;
    private final String name;

    /**
     * Creates a new Ingredient
     * @param name Contains exact name
     * @param price Contains exact price
     * @param ticksToPrepare Contains exact ticks to prepare
     */
    public Ingredient(String name, float price, int ticksToPrepare){
        this.price=price;
        this.name=name;
        this.ticksToPrepare = ticksToPrepare;
    }

    /**
     * Gets a price
     * @return price of ingredient
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * Gets a name
     * @return name of ingredients
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets ticks to prepare ingredient
     * @return Number ticks to prepare
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
