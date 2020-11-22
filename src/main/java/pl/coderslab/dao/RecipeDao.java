package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?,?,?,NOW(),NOW(),?,?,?)";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id=?";
    // TODO Nie można wykorzystywać SELECT bez ORDER do stronicowania!
    private static final String FIND_ALL_RECIPE_QUERY = "SELECT * FROM recipe";
    private static final String READ_RECIPE_QUERY = "SELECT * FROM recipe WHERE id=?";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ? , ingredients = ?, description = ?, updated = NOW(), preparation_time = ?, preparation = ? WHERE id = ?";
    private static final String FIND_ALL_RECIPE_USER = "SELECT * FROM recipe WHERE admin_id=? ORDER BY created DESC";
    private static final String FIND_ALL_RECIPE_PLAN = "SELECT * FROM recipe_plan WHERE recipe_id = ?";

    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)) {
            statement.setInt(1, recipeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setCreated(resultSet.getString("created"));
                    recipe.setUpdated(resultSet.getString("updated"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }
    public List<Recipe> findAllSearch(String param){
        List<Recipe> recipeList = findAllDefault(null,"SELECT * FROM recipe WHERE name LIKE '"+param+"%';");
        return recipeList;
    }
    public List<Recipe> findAllDefault(Admin admin,String query){
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if(admin!=null) {
                statement.setInt(1, admin.getId());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients(resultSet.getString("ingredients"));
                recipeToAdd.setDescription(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getString("created"));
                recipeToAdd.setUpdated(resultSet.getString("updated"));
                recipeToAdd.setPreparationTime(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipeToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    public List<Recipe> findAllForUser(Admin admin){
        return findAllDefault(admin,FIND_ALL_RECIPE_USER);
    }
    public List<Recipe> findAll() {
        return findAllDefault(null,FIND_ALL_RECIPE_QUERY);
    }

    public Recipe create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertRecipe = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertRecipe.setString(1, recipe.getName());
            insertRecipe.setString(2, recipe.getIngredients());
            insertRecipe.setString(3, recipe.getDescription());
            insertRecipe.setInt(4, recipe.getPreparationTime());
            insertRecipe.setString(5, recipe.getPreparation());
            insertRecipe.setInt(6, recipe.getAdminId());
            int result = insertRecipe.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertRecipe.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY)) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY)) {
            statement.setInt(6, recipe.getId());
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setInt(4, recipe.getPreparationTime());
            statement.setString(5, recipe.getPreparation());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String SELECT_ALL_RECIPIES_BY_ID_QUERY = "SELECT COUNT(*) AS result FROM recipe WHERE admin_id = ?";

    public int getRecipiesById(Admin admin) {
        int numberOfRecipies = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RECIPIES_BY_ID_QUERY)) {
            statement.setInt(1, admin.getId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            numberOfRecipies = rs.getInt("result");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return numberOfRecipies;
    }
    public String checkRecipePlan(Recipe recipe){
        String checkRecipePlan = "";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPE_PLAN)){
            statement.setInt(1, recipe.getId());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                checkRecipePlan= "yes";
            }else{
                checkRecipePlan= "no";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return checkRecipePlan;
    }

}

