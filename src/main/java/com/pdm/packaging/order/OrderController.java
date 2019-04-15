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
                           @RequestParam(value="isPrepaid", defaultValue = "-1") Integer is_prepaid,
                           @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                           @RequestParam(value="completed", defaultValue = "-1") Integer completed) {
        String orderCall = "select *,s.name as sname,r.name as rname from orders left outer join users as s on orders.sender_ID=s.user_ID left outer join users as r on orders.receiver_ID=r.user_ID";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (sender_ID > 0) arguments.put("sender_ID", sender_ID.toString());
        if (receiver_ID > 0) arguments.put("receiver_ID", receiver_ID.toString());
        if (is_prepaid == 0 || is_prepaid == 1) arguments.put("is_prepaid", is_prepaid.toString());
        if (cost >= 0.0) arguments.put("cost", cost.toString());
        if (completed == 0 || completed == 1) arguments.put("is_complete", completed.toString());
        orderCall = h2.buildQuery(orderCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet orders = h2.query(orderCall);
            while (orders.next()) {
                ResultSetMetaData rsmd = orders.getMetaData();
                for (int i = 1; i < rsmd.getColumnCount() - 1; i++) {
                    System.out.println(i + " : " + rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
                }
                results.addData(new Order(orders.getInt("order_ID"),
                        orders.getString("sname"),
                        orders.getString("rname"),
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
                           @RequestParam(value="isPrepaid", defaultValue = "-1") Integer is_prepaid,
                           @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                           @RequestParam(value="completed", defaultValue = "-1") Integer completed) {
        String orderCall = "select * from orders";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (order_ID > 0) arguments.put("order_ID", order_ID.toString());
        if (is_prepaid == 0 || is_prepaid == 1) arguments.put("is_prepaid", is_prepaid.toString());
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

    @CrossOrigin
    @RequestMapping("/order/add/")
    public QueryData addOrder(@RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                           @RequestParam(value="userID", defaultValue = "0") Integer user_ID,
                              @RequestParam(value="senderID", defaultValue = "0") Integer sender_ID,
                           @RequestParam(value="receiverID", defaultValue = "0") Integer receiver_ID,
                           @RequestParam(value="isPrePaid", defaultValue = "-1") Integer pre_paid,
                           @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                           @RequestParam(value="completed", defaultValue = "-1") Integer completed) {

        String str = "insert into orders (is_prepaid, sender_ID, receiver_ID, cost, is_complete) values (";
        str = str + pre_paid + "," + sender_ID  + "," + receiver_ID  + "," + cost + ",0);";
        QueryData results = new QueryData();

        try {
            ResultSet newerOrder = h2.execute(str);
            newerOrder = h2.query("select MAX(order_ID) as ID from orders");
            
            if (newerOrder.next()) {
                results.addData(new Order(newerOrder.getInt("ID")));

            } else {
                results = h2.errorCall(results, str);
            }
        } catch (SQLException se) {
            results = h2.errorCall(results, str);
        }
        return results;
    }
}
