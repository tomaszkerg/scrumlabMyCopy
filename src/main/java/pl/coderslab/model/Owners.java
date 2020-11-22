package pl.coderslab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Owners {
    private int id;
    private String name;
    private String adress;
    private String telefon;
    private String email;

}
