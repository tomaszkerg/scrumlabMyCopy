package pl.coderslab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Plan {

    private int id;
    private String name;
    private String description;
    private String created;
    private int adminId;
    private int lp;
}
