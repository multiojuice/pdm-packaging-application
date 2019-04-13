package com.pdm.packaging.business;

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
public class BusinessController {

    @RequestMapping("/business")
    public QueryData business(@RequestParam(value="businessID", defaultValue = "0") Integer business_ID,
                              @RequestParam(value="name", defaultValue = "") String name,
                              @RequestParam(value="address", defaultValue = "") String address) {
        String businessCall = "select * from business";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (business_ID > 0) arguments.put("business_ID", business_ID.toString());
        if (!name.equals("")) arguments.put("name", name);
        if (!address.equals("")) arguments.put("address", address);
        businessCall = h2.buildQuery(businessCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet businesses = h2.query(businessCall);
            while (businesses.next()) {
                results.addData(new Business(businesses.getInt("business_ID"),
                        businesses.getString("name"),
                        businesses.getString("address")));
            }
        } catch (SQLException se) {
            System.out.println("Error parsing results of query '" + businessCall + "'");
            results.setData(new ArrayList<Object>(){{add(new Exception("Could not complete request"));}});
            results.setCount(-1);
        }
        return results;
    }
}
