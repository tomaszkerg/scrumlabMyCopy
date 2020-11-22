package pl.coderslab.dao;

import pl.coderslab.model.AboutApp;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class AboutAppDao {

    private static final String READ_ABOUT_QUERY = "SELECT * FROM about WHERE id = ?;";

    // TODO Jak rozpoznawać czy się udało czy nie?

//    public void test() {
//        Optional<AboutApp> appOptional = find(1);
//        appOptional.ifPresentOrElse(request.addAttribute(""), () -> response.sendRedirect("/"));
//    }

    public Optional<AboutApp> find(Integer aboutAppId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ABOUT_QUERY)
        ) {
            statement.setInt(1, aboutAppId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AboutApp aboutApp = new AboutApp();
                    aboutApp.setId(resultSet.getInt("id"));
                    aboutApp.setDesc(resultSet.getString("desc"));
                    return Optional.of(aboutApp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public AboutApp read(Integer aboutAppId) {
        AboutApp aboutApp = new AboutApp();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ABOUT_QUERY)
        ) {
            statement.setInt(1, aboutAppId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    aboutApp.setId(resultSet.getInt("id"));
                    aboutApp.setDesc(resultSet.getString("desc"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aboutApp;

    }
}
