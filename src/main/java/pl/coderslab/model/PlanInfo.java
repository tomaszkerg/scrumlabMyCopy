package pl.coderslab.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PlanInfo {
    private String dayName;
    private String mealName;
    private String recipeName;
    private String recipeDescription;
    private int recipeId;
    private int recipePlanId;

}
