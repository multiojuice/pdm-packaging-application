package com.pdm.packaging.order;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import com.pdm.packaging.QueryData;
import com.pdm.packaging.packages.PackageController;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.web.bind.annotation.*;

import static com.pdm.packaging.PackagingApplication.h2;

@RestController
public class OrderController {

    @CrossOrigin
    @RequestMapping("/order")
    public QueryData order(@RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                           @RequestParam(value="senderID", defaultValue = "0") Integer sender_ID,
                           @RequestParam(value="receiverID", defaultValue = "0") Integer receiver_ID,
                           @RequestParam(value="isPrePaid", defaultValue = "-1") Integer pre_paid,
                           @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                           @RequestParam(value="completed", defaultValue = "-1") Integer completed) {
        String orderCall = "select * from orders inner join users s on orders.sender_ID=s.user_ID inner join users r on orders.receiver_ID=r.user_ID";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (sender_ID > 0) arguments.put("sender_ID", sender_ID.toString());
        if (receiver_ID > 0) arguments.put("receiver_ID", receiver_ID.toString());
        if (pre_paid == 0 || pre_paid == 1) arguments.put("is_prepaid", pre_paid.toString());
        if (cost >= 0.0) arguments.put("cost", cost.toString());
        if (completed == 0 || completed == 1) arguments.put("is_complete", completed.toString());
        orderCall = h2.buildQuery(orderCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet orders = h2.query(orderCall);
            while (orders.next()) {
                ResultSetMetaData rsmd = orders.getMetaData();
                for (int i = 1; i < rsmd.getColumnCount() - 1; i++) {
                    System.out.println(i + " : " + rsmd.getColumnName(i));
                }
                results.addData(new Order(orders.getInt("order_ID"),
                        orders.getString("s.name"),
                        orders.getString("r.name"),
                        orders.getBoolean("is_prepaid"),
                        orders.getDouble("cost"),
                        orders.getBoolean("is_complete")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            results = h2.errorCall(results, orderCall);
        }
        return results;
    }


    @CrossOrigin
    @RequestMapping("/order/user")
    public QueryData orderFromUser(@RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                           @RequestParam(value="userID", defaultValue = "0") Integer user_ID,
                           @RequestParam(value="isPrePaid", defaultValue = "-1") Integer pre_paid,
                           @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                           @RequestParam(value="completed", defaultValue = "-1") Integer completed) {
        String orderCall = "select * from orders";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (pre_paid == 0 || pre_paid == 1) arguments.put("is_prepaid", pre_paid.toString());
        if (cost >= 0.0) arguments.put("cost", cost.toString());
        if (completed == 0 || completed == 1) arguments.put("is_complete", completed.toString());

        orderCall = h2.buildQuery(orderCall, arguments);
        String userIDStr = "(sender_ID = " + user_ID.toString() + " or receiver_ID = " + user_ID.toString() + ");";

        userIDStr = orderCall.contains("where") ? " and " + userIDStr : " where " + userIDStr;
        System.out.println(userIDStr);
        orderCall = orderCall.replace(";", userIDStr);

        System.out.println(orderCall);
        QueryData results = new QueryData();
        try {
            ResultSet orders = h2.query(orderCall);
            while (orders.next()) {
                results.addData(new Order(orders.getInt("order_ID"),
                        orders.getInt("sender_ID"),
                        orders.getInt("receiver_ID"),
                        orders.getBoolean("is_prepaid"),
                        orders.getDouble("cost"),
                        orders.getBoolean("is_complete")));
            }
        } catch (SQLException se) {
            results = h2.errorCall(results, orderCall);
        }
        return results;
    }


    @PostMapping("/order/add/")
    public QueryData addOrder(@RequestBody Order newOrder,
                              @RequestBody List<com.pdm.packaging.packages.Package> packageList) {
        String orderAdd = "insert into order (";
        String attributes = "";
        Integer sender_ID = newOrder.getSenderID();
        Integer receiver_ID = newOrder.getReceiverID();
        Integer pre_paid = newOrder.isPrePaid() ? 1 : 0;
        Double cost = newOrder.getCost();
        Integer completed = newOrder.isComplete() ? 1 : 0;
        if (sender_ID > 0) orderAdd += "sender_ID, "; attributes += sender_ID.toString() + ", ";
        if (receiver_ID > 0) orderAdd += "receiver_ID, "; attributes += receiver_ID.toString() + ", ";
        orderAdd += "is_prepaid, ";
        attributes += pre_paid.toString() + ", ";
        if (cost >= 0.0) orderAdd += "cost, "; attributes += cost.toString() + ", ";
        orderAdd += "is_complete, ";
        attributes += completed.toString() + ", ";
        orderAdd = orderAdd.substring(0, orderAdd.length() - 3);
        orderAdd += ") values (" + attributes.substring(0, attributes.length() - 3) + ");";
        QueryData results = new QueryData();
        try {
            ResultSet newerOrder = h2.query(orderAdd);
            if (newerOrder.next()) {
                results.addData(new Order(newerOrder.getInt("order_ID"),
                        newerOrder.getInt("sender_ID"),
                        newerOrder.getInt("receiver_ID"),
                        newerOrder.getBoolean("is_prepaid"),
                        newerOrder.getDouble("cost"),
                        newerOrder.getBoolean("is_complete")));
                for (com.pdm.packaging.packages.Package p: packageList) {
                    PackageController.addPackage(p, newerOrder.getInt("order_ID"));
                }
            } else {
                results = h2.errorCall(results, orderAdd);
            }
        } catch (SQLException se) {
            results = h2.errorCall(results, orderAdd);
        }
        return results;
    }
}
