package pl.coderslab.dao;


import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;


import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins WHERE id=?";
    private static final String READ_ADMIN_QUERY_ID = "SELECT * FROM admins WHERE id=?";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins";
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name,last_name,email,password,superadmin,enable) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ? , last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE id = ?";
    private static final String READ_ADMIN_QUERY_EMAIL = "SELECT * FROM admins WHERE email=?";

    public Admin read(Integer adminId) {
        String value = adminId.toString();
        Admin admin = readDefault(value,READ_ADMIN_QUERY_ID);
        return admin;
    }
    public Admin readByEmail(String email){
        Admin admin = readDefault(email,READ_ADMIN_QUERY_EMAIL);
        return admin;
    }
    public Admin readDefault(String param,String query){
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    createAdminFromResult(resultSet, admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // TODO Powtarzający się kdo
                Admin adminToAdd = new Admin();
                createAdminFromResult(resultSet, adminToAdd);
                adminList.add(adminToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    private void createAdminFromResult(ResultSet resultSet, Admin adminToAdd) throws SQLException {
        adminToAdd.setId(resultSet.getInt("id"));
        adminToAdd.setFirstName(resultSet.getString("first_name"));
        adminToAdd.setLastName(resultSet.getString("last_name"));
        adminToAdd.setEmail(resultSet.getString("email"));
        adminToAdd.setPassword(resultSet.getString("password"));
        adminToAdd.setSuperAdmin(resultSet.getInt("superadmin"));
        adminToAdd.setEnable(resultSet.getInt("enable"));
    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getSuperAdmin());
            statement.setInt(6, admin.getEnable());
            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(Admin admin){
        String password = admin.getPassword();
        admin.setPassword(hashPassword(password));
        update(admin);
    }

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY)) {
            statement.setInt(7, admin.getId());
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPassword());
            statement.setInt(5, admin.getSuperAdmin());
            statement.setInt(6, admin.getEnable());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            int i = statement.executeUpdate();
            if (i < 1) {
                throw new NotFoundException("Admin not found");
            }

            // TODO To jest bez sensu ;)
            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Admin not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    // TODO Tekst jako odpowiedź, enum
    public static final String CHECK_LOGIN_OK = "ok";

    public String checkLogin(String email, String password) {
        Admin admin = readDefault(email,READ_ADMIN_QUERY_EMAIL);
        if (email.equals(admin.getEmail())) {
            if (BCrypt.checkpw(password, admin.getPassword())) {
                return CHECK_LOGIN_OK;
            } else {
                return "notok";
            }
        }
        return "notexist";
    }

    // TODO Błędna logika
    public boolean checkIfLoginExist (String email) {
        Admin admin = readDefault(email, READ_ADMIN_QUERY_EMAIL);
        if (email.equals(admin.getEmail())) {return true;}
        else return false;
    }
}
