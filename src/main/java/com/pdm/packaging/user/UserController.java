package com.pdm.packaging.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.pdm.packaging.QueryData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.pdm.packaging.PackagingApplication.h2;

@RestController
public class UserController {

    @RequestMapping
    public QueryData user(@RequestParam(value="userID", defaultValue = "0") Integer user_ID,
                          @RequestParam(value="name", defaultValue = "") String name,
                          @RequestParam(value="isPremium", defaultValue = "-1") Integer is_premium,
                          @RequestParam(value="phoneNumber", defaultValue = "0") Integer phone_number,
                          @RequestParam(value="businessID", defaultValue = "0") Integer business_ID) {
        String userCall = "select * from users";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (user_ID > 0) arguments.put("user_ID", user_ID.toString());
        if (!name.equals("")) arguments.put("name", name);
        if (is_premium > -1) arguments.put("is_premium", is_premium.toString());
        if (phone_number > 0) arguments.put("phone_number", phone_number.toString());
        if (business_ID > 0) arguments.put("business_ID", business_ID.toString());
        userCall = h2.buildQuery(userCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet orders = h2.query(userCall);
            while (orders.next()) {
                results.addData(new User(orders.getInt("user_ID"),
                        orders.getString("name"),
                        orders.getBoolean("is_premium"),
                        orders.getInt("phone_number"),
                        orders.getInt("business_ID")));
            }
        } catch (SQLException se) {
            System.out.println("Error parsing results of query '" + userCall + "'");
            results.setData(new ArrayList<Object>(){{add(new Exception("Could not complete request"));}});
            results.setCount(-1);
        }
        return results;
    }
}
