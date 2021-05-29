package RestaurantSim;

public class Ingredient {
    private float price;
    private String name;

    public Ingredient(float price, String name) {
        this.price=price;
        this.name=name;
    }

    public float getPrice()
    {
        return price;
    }
    public String getName() {
        return name;
    }
}
