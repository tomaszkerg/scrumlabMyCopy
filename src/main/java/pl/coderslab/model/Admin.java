package pl.coderslab.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// TODO @ToString zawiera się już w @Data
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Admin {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int superAdmin;
    private int enable;
}