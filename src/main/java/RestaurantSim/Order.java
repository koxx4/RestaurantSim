package RestaurantSim;

import java.util.List;


public class Order
{
     private final List<Dish> dishes;

    public Order(List<Dish> dishes)
    {
        this.dishes = dishes;
    }

    public float GetTotalPrice()
    {
        float totalPrice = 0;
        for (var dish: dishes)
        {
            totalPrice += dish.GetPrice();
        }
        return totalPrice;
    }


    public List<Dish> GetDishes()
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
