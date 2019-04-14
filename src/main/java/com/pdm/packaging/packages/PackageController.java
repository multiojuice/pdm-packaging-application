package com.pdm.packaging.packages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.pdm.packaging.QueryData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.pdm.packaging.PackagingApplication.h2;

@RestController
public class PackageController {
    @CrossOrigin
    @RequestMapping("/package")
    public QueryData packages(@RequestParam(value="packageID", defaultValue = "0") Integer package_ID,
                              @RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                              @RequestParam(value="shippingStatus", defaultValue = "-1") Integer shipping_status,
                              @RequestParam(value="weight", defaultValue = "0") Integer weight,
                              @RequestParam(value="deliveryTime", defaultValue = "0") Integer delivery_time,
                              @RequestParam(value="trait", defaultValue = "0") Integer trait,
                              @RequestParam(value="trackingID", defaultValue = "0") Integer tracking_ID) {
        String packageCall = "select * from package inner join traits on package.trait=traits.trait_ID";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (package_ID > 0) arguments.put("package_ID", package_ID.toString());
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (shipping_status >= 1 && shipping_status <= 5) arguments.put("shipping_status", shipping_status.toString());
        if (weight > 0) arguments.put("weight", weight.toString());
        if (delivery_time > 0) arguments.put("delivery_time", delivery_time.toString());
        if (tracking_ID > 0) arguments.put("tracking_ID", tracking_ID.toString());
        if (trait >= 1 && trait <= 5) arguments.put("trait", trait.toString());
        packageCall = h2.buildQuery(packageCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet packages = h2.query(packageCall);
            while (packages.next()) {
                results.addData(new Package(packages.getInt("package_ID"),
                        packages.getInt("order_ID"),
                        packages.getInt("shipping_status"),
                        packages.getInt("weight"),
                        packages.getInt("delivery_time"),
                        packages.getInt("traits.trait_ID"),
                        packages.getString("traits.name"),
                        packages.getInt("tracking_ID")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            results = h2.errorCall(results, packageCall);
        } catch (Exception e) {
            e.printStackTrace();
            results = h2.errorCall(results, packageCall);
        }
        return results;
    }

    public static boolean addPackage(Package p, Integer order_ID) {
        String addPackageCall = "insert into package (";
        String attributes = "";
        attributes += order_ID.toString() + ", ";
        Integer package_ID = p.getPackageID(); attributes += package_ID.toString() + ", ";
        Integer shipping_status = p.getShippingStatus(); attributes += shipping_status.toString() + ", ";
        Integer weight = p.getWeight(); attributes += weight.toString() + ", ";
        Integer delivery_time = p.getDeliveryTime(); attributes += delivery_time.toString() + ", ";
        Integer trait_ID = p.getTraitID(); attributes += trait_ID.toString() + ", ";
        Integer tracking_ID = p.getTrackingID(); attributes += tracking_ID.toString() + ", ";
        addPackageCall += "order_ID, ";
        addPackageCall += "package_ID, ";
        addPackageCall += "shipping_status, ";
        addPackageCall += "weight, ";
        addPackageCall += "delivery_time, ";
        addPackageCall += "trait, ";
        addPackageCall += "tracking_ID, ";
        addPackageCall = addPackageCall.substring(0, addPackageCall.length() - 3);
        addPackageCall += ") values (" + attributes.substring(0, attributes.length() - 3) + ");";
        QueryData results = new QueryData();
        try {
            ResultSet packageAdd = h2.query(addPackageCall);
            if (packageAdd.next()) {
                return true;
            } else {
                results = h2.errorCall(results, addPackageCall);
            }
        }  catch (SQLException se) {
            results = h2.errorCall(results, addPackageCall);
        }
        return false;
    }
}
