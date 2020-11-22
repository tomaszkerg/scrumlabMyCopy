package pl.coderslab.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RecipePlan {
    private int id;
    private int recipeId;
    private String mealName;
    private int displayOrder;
    private int dayNameId;
    private int planId;
}
