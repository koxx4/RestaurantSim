package RestaurantSim;

import java.util.List;

/**
 * This class contains all the crucial information about order that the {@link Restaurant}
 * can process.
 */
public class Order
{
    private final List<Dish> dishes;

    /**
     * Creates the object of this class
     * @param dishes List of dishes that this order consists of
     */
    public Order(List<Dish> dishes)
    {
        this.dishes = dishes;
    }

    /**
     * @return Total price that is the sum of all dish prices that this order consists of
     */
    public float getTotalPrice() {
        float totalPrice = 0;
        for (var dish: dishes) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }

    /**
     * @return List of dishes that this order consist of
     */
    public List<Dish> getDishes()
    {
        return dishes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---ORDER---\n");
        for(var dish : dishes){
            stringBuilder.append("Dish name: " + dish.getName() +" ");
            stringBuilder.append("ingredients:\n");
            for ( var ingredient : dish.getIngredients() ){
                stringBuilder.append("\u2726" + ingredient.getName() + "\n");
            }
            stringBuilder.append("-----------\n");
        }
        return stringBuilder.toString();
    }
}
