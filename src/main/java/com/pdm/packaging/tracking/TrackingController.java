package com.pdm.packaging.tracking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.pdm.packaging.QueryData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.pdm.packaging.PackagingApplication.h2;

@RestController
public class TrackingController {
    @CrossOrigin
    @RequestMapping("/tracking")

    public QueryData tracking(@RequestParam(value = "trackingID", defaultValue = "0") Integer tracking_ID,
                              @RequestParam(value = "transportID", defaultValue = "0") Integer transport_ID,
                              @RequestParam(value = "currentlocationID", defaultValue = "0") Integer current_location_ID ) {

        String trackingCall = "select * from tracking";
        LinkedHashMap<String, String> arguments = new LinkedHashMap<>();

        if(tracking_ID > 0) { arguments.put("tracking_ID", tracking_ID.toString()); }
        if(transport_ID > 0) { arguments.put("transport_ID", transport_ID.toString()); }
        if(current_location_ID > 0) { arguments.put("current_location_ID", current_location_ID.toString()); }

        trackingCall = h2.buildQuery(trackingCall, arguments);
        QueryData results = new QueryData();

        try {
            ResultSet tracking = h2.query(trackingCall);
            while(tracking.next()) {
                results.addData(new Tracking(tracking.getInt("tracking_ID"),
                        tracking.getInt("transport_ID"),
                        tracking.getInt("current_location_ID")));
            }

        } catch (SQLException se) {
            results = h2.errorCall(results, trackingCall);
        }

        return results;
    }


}
