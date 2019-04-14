package com.pdm.packaging.order;

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
public class OrderController {

    @CrossOrigin
    @RequestMapping("/order")
    public QueryData order(@RequestParam(value="orderID", defaultValue = "0") Integer order_ID,
                       @RequestParam(value="senderID", defaultValue = "0") Integer sender_ID,
                       @RequestParam(value="receiverID", defaultValue = "0") Integer receiver_ID,
                       @RequestParam(value="isPrePaid", defaultValue = "-1") Integer pre_paid,
                       @RequestParam(value="cost", defaultValue = "-0.01") Double cost,
                       @RequestParam(value="completed", defaultValue = "-1") Integer completed) {
        String orderCall = "select * from orders";
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
}
