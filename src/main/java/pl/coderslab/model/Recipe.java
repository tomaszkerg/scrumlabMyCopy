package pl.coderslab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String description;
    private String created;
    private String updated;
    private int preparationTime;
    private String preparation;
    private int adminId;
    // TODO Logika związana z prezentacją umiejscowiona w klasie modelowej, zło!!!!
    private int lp;
}
