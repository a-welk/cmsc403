public class FoodBank {

    int food;

    public FoodBank() {
        food = 0;
    }

    public void takeFood(int amount) {
        food -= amount;
    }

    public void giveFood(int amount) {
        food += amount;
    }
}