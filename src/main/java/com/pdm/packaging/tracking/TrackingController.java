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

        String trackingCall = "select * from tracking left outer join locations on tracking.current_location_ID=locations.location_ID left outer join transport on tracking.transport_ID=transport.transport_ID";
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
                        tracking.getString("type"),
                        tracking.getInt("current_location_ID"),
                        tracking.getString("location_name")));
            }

        } catch (SQLException se) {
            results = h2.errorCall(results, trackingCall);
        }

        return results;
    }

    @CrossOrigin
    @RequestMapping("/tracking/stops")
    public QueryData trackingLocations(@RequestParam(value = "locationID", defaultValue = "0") Integer location_ID ) {

        String trackingCall = "select * from location_view inner join stops where location_view.tracking_id = stops.tracking_id and stops.stop_num = location_view.stop_num + 1 and stops.location_id = "
                + location_ID.toString();

        QueryData results = new QueryData();

        try {
            ResultSet tracking = h2.query(trackingCall);
            while(tracking.next()) {
                results.addData(new Tracking(tracking.getInt("tracking_ID"),
                        tracking.getInt("location_view.transport_ID"),
                        tracking.getInt("location_view.current_location_ID")));
            }

        } catch (SQLException se) {
            se.printStackTrace();
            results = h2.errorCall(results, trackingCall);
        }

        return results;
    }
}
