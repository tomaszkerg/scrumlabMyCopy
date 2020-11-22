package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanInfo;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    // ZAPYTANIA SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,NOW(),?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan WHERE id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan WHERE admin_id=? ORDER BY created DESC;";
    private static final String READ_PLAN_QUERY = "SELECT * FROM plan WHERE id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ? WHERE id = ?;";
    private static final String SELECT_ALL_PLANS_BY_ID_QUERY = "SELECT COUNT(*) AS result FROM plan WHERE admin_id = ?";
    private static final String FIND_PLAN_NAME = "SELECT name FROM plan WHERE admin_id = ? ORDER BY created DESC LIMIT 1;";
    private static final String FIND_PLAN_INFO = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe_id " +
            "FROM `recipe_plan` JOIN day_name on day_name.id=day_name_id JOIN recipe on recipe.id=recipe_id WHERE recipe_plan.plan_id = (SELECT MAX(id) from plan WHERE admin_id = ?)" +
            " ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String CREATE_RECIPE_PLAN_QUERY = "INSERT INTO recipe_plan(recipe_id,meal_name,display_order,day_name_id,plan_id) VALUES(?,?,?,?,?);";
    private static final String DELETE_RECIPE_PLAN_QUERY = "DELETE FROM recipe_plan WHERE id = ?";
    private static final String DELETE_ALL_RECIPES_FOR_PLAN_QUERY = "DELETE FROM recipe_plan WHERE plan_id=?";

    private static final String FIND_PLAN_INFO_BYID = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description, recipe_id, recipe_plan.id as recipeplan_id " +
            "FROM `recipe_plan` JOIN day_name on day_name.id=day_name_id JOIN recipe on recipe.id=recipe_id WHERE plan_id = ? " +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    /**
     * Get plan by id
     *
     * @param planId
     * @return
     */


    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    /**
     * Return all plans
     *
     * @return
     */
    public List<Plan> findAllForUser(Admin admin) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);) {
            statement.setInt(1, admin.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Plan planToAdd = new Plan();
                    planToAdd.setId(resultSet.getInt("id"));
                    planToAdd.setName(resultSet.getString("name"));
                    planToAdd.setDescription(resultSet.getString("description"));
                    planToAdd.setCreated(resultSet.getString("created"));
                    planList.add(planToAdd);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    /**
     * Create plan
     *
     * @param plan
     * @return
     */
    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setInt(3, plan.getAdminId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove plan by id
     *
     * @param planId
     */
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Update plan
     *
     * @param plan
     */
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(3, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPlansById(Admin admin) {
        int numberOfPlans = 0;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PLANS_BY_ID_QUERY)) {
            statement.setInt(1, admin.getId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            numberOfPlans = rs.getInt("result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfPlans;
    }

    public String getPlanName(Admin admin) {
        String planName = "Brak planów";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_NAME)) {
            statement.setInt(1, admin.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                planName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planName;
    }

    public List<PlanInfo> getPlanInfo(Admin admin) {
        List<PlanInfo> planInfoAll = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_INFO, ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {
            statement.setInt(1, admin.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    PlanInfo planInfo = new PlanInfo();
                    planInfo.setDayName(resultSet.getString("day_name"));
                    planInfo.setMealName(resultSet.getString("meal_name"));
                    planInfo.setRecipeName(resultSet.getString("recipe_name"));
                    planInfo.setRecipeDescription(resultSet.getString("recipe_description"));
                    planInfo.setRecipeId(resultSet.getInt("recipe_id"));
                    planInfoAll.add(planInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planInfoAll;
    }

    public List<PlanInfo> getPlanInfoById(Plan plan) {
        List<PlanInfo> planInfoAll = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_INFO_BYID, ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE)) {
            statement.setInt(1, plan.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    PlanInfo planInfo = new PlanInfo();
                    planInfo.setDayName(resultSet.getString("day_name"));
                    planInfo.setMealName(resultSet.getString("meal_name"));
                    planInfo.setRecipeName(resultSet.getString("recipe_name"));
                    planInfo.setRecipeDescription(resultSet.getString("recipe_description"));
                    planInfo.setRecipeId(resultSet.getInt("recipe_id"));
                    planInfo.setRecipePlanId(resultSet.getInt("recipeplan_id"));
                    planInfoAll.add(planInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planInfoAll;
    }

    public RecipePlan createRecipeForPlan(RecipePlan recipePlan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertRecipePlan = connection.prepareStatement(CREATE_RECIPE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertRecipePlan.setInt(1, recipePlan.getRecipeId());
            insertRecipePlan.setString(2, recipePlan.getMealName());
            insertRecipePlan.setInt(3, recipePlan.getDisplayOrder());
            insertRecipePlan.setInt(4, recipePlan.getDayNameId());
            insertRecipePlan.setInt(5, recipePlan.getPlanId());
            int result = insertRecipePlan.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertRecipePlan.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipePlan.setId(generatedKeys.getInt(1));
                    return recipePlan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteRecipeFromPlan(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Plan> findAllDefault(Admin admin, String query) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (admin != null) {
                statement.setInt(1, admin.getId());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planList.add(planToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public void deleteAllforPlan(Integer planId) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_RECIPES_FOR_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
