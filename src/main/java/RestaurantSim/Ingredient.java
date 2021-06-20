package RestaurantSim;

public class Ingredient
{
    private final float price;
    private final int ticksToPrepare;
    private final String name;

    public Ingredient(String name, float price, int ticksToPrepare) {
        this.price=price;
        this.name=name;
        this.ticksToPrepare = ticksToPrepare;
    }

    public float getPrice()
    {
        return price;
    }
    public String getName()
    {
        return name;
    }
    public int getTicksToPrepare()
    {
        return ticksToPrepare;
    }

    @Override
    public String toString() {
        return "Ingredient{" + ", name='" + name + '}';
    }
}
