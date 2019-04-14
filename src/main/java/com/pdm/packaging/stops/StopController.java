package com.pdm.packaging.stops;

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
public class StopController {
    @CrossOrigin
    @RequestMapping("/stop")
    public QueryData business(@RequestParam(value="trackingID", defaultValue = "0") Integer tracking_ID,
                              @RequestParam(value="locationID", defaultValue = "0") Integer location_ID,
                              @RequestParam(value="stopNum", defaultValue = "0") Integer stop_num) {
        String stopCall = "select * from stop";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();
        if (tracking_ID > 0) arguments.put("tracking_ID", tracking_ID.toString());
        if (location_ID > 0) arguments.put("location_ID", location_ID.toString());
        if (stop_num > 0) arguments.put("stop_num",stop_num.toString());
        stopCall = h2.buildQuery(stopCall, arguments);
        QueryData results = new QueryData();
        try {
            ResultSet stops = h2.query(stopCall);
            while (stops.next()) {
                results.addData(new Stop(stops.getInt("tracking_ID"),
                        stops.getInt("location_ID"),
                        stops.getInt("stop_num")));
            }
        } catch (SQLException se) {
            results = h2.errorCall(results, stopCall);
        }
        return results;
    }
}
