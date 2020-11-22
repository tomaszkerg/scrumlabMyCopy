package pl.coderslab.dao;

import pl.coderslab.model.Admin;
import pl.coderslab.model.DayName;
import pl.coderslab.model.PlanInfo;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayNameDao {

    private static final String FIND_ALL_DAYNAME_QUERY = "SELECT * FROM day_name";

    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAYNAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;
    }

    // TODO Logika biznesowa w kodzie DAO
    public List<DayName> dayNames(List<PlanInfo> getPlanInfo){
        // LinkedHashSet
        List<DayName> dayNames = new ArrayList<>();
        List<DayName> dayList = findAll();
        for(PlanInfo p : getPlanInfo){
            for(DayName o : dayList){
                if(p.getDayName().equals(o.getName())){
                    if(!dayNames.contains(o)) dayNames.add(o);
                }
            }
        }
        return dayNames;
    }
}
