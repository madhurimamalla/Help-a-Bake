package mmalla.android.com.helpabake;

public class Ingredient {

    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int id, String measure, String ingredient) {
        this.quantity = id;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
