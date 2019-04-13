package com.pdm.packaging.packages;

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
public class PackageController {

    @RequestMapping("/package")
    public QueryData business(@RequestParam(value="packageID", defaultValue = "0") Integer package_ID,
                              @RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                              @RequestParam(value="shippingStatus", defaultValue = "-1") Integer shipping_status,
                              @RequestParam(value="weight", defaultValue = "0") Integer weight,
                              @RequestParam(value="deliveryTime", defaultValue = "0") Integer delivery_time,
                              @RequestParam(value="trait", defaultValue = "-1") Integer trait,
                              @RequestParam(value="trackingID", defaultValue = "0") Integer tracking_ID) {
        String packageCall = "select * from business";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (package_ID > 0) arguments.put("package_ID", package_ID.toString());
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (shipping_status > -1) arguments.put("shipping_status", shipping_status.toString());
        if (weight > 0) arguments.put("weight", weight.toString());
        if (delivery_time > 0) arguments.put("delivery_time", delivery_time.toString());
        if (trait > -1) arguments.put("trait", trait.toString());
        if (tracking_ID > 0) arguments.put("tracking_ID", tracking_ID.toString());
        packageCall = h2.buildQuery(packageCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet packages = h2.query(packageCall);
            while (packages.next()) {
                results.addData(new Package(packages.getInt("package_ID"),
                        packages.getInt("order_ID"),
                        packages.getInt("shipping_status"),
                        packages.getInt("weight"),
                        packages.getInt("deliver_time"),
                        packages.getInt("trait"),
                        packages.getInt("tracking_ID")));
            }
        } catch (SQLException se) {
            System.out.println("Error parsing results of query '" + packageCall + "'");
            results.setData(new ArrayList<Object>(){{add(new Exception("Could not complete request"));}});
            results.setCount(-1);
        }
        return results;
    }
}
