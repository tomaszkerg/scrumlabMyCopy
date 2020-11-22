package pl.coderslab.dao;

import pl.coderslab.model.Book;
import pl.coderslab.model.ContactData;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ContactDataDao {

    private static final String CREATE_CONTACT_QUERY = "INSERT INTO contact(name,email,message) VALUES (?,?,?);";

    public ContactData create(ContactData contactData) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_CONTACT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, contactData.getName());
            insertStm.setString(2, contactData.getEmail());
            insertStm.setString(3, contactData.getMessage());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    contactData.setId(generatedKeys.getInt(1));
                    return contactData;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
