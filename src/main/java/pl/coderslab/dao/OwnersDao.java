package pl.coderslab.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.model.Book;
import pl.coderslab.model.Owners;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class OwnersDao {

    private static final String READ_OWNER_QUERY = "SELECT * from owners where id = ?;";

    public Owners read(Integer ownerId) {
        Owners owner = new Owners();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_OWNER_QUERY)
        ) {
            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    owner.setId(resultSet.getInt("id"));
                    owner.setName(resultSet.getString("name"));
                    owner.setAdress(resultSet.getString("adress"));
                    owner.setTelefon(resultSet.getString("telefon"));
                    owner.setEmail(resultSet.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return owner;

    }
}