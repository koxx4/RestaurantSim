package RestaurantSim;

public class Ingredient
{
    private final float price;
    private final int ticksToPrepare;
    private final String name;

    public Ingredient(String name, float price, int ticksToPrepare)
    {
        this.price=price;
        this.name=name;
        this.ticksToPrepare = ticksToPrepare;
    }

    public float GetPrice()
    {
        return price;
    }
    public String GetName()
    {
        return name;
    }
    public int GetTicksToPrepare()
    {
        return ticksToPrepare;
    }
}
