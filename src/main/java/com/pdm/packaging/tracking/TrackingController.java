package com.pdm.packaging.tracking;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import com.pdm.packaging.QueryData;
import com.pdm.packaging.stops.Stop;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.tree.Tree;

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

        String trackingCall = "select * from location_view left outer join locations as lname on lname.location_ID=location_view.location_ID inner join stops where location_view.tracking_id = stops.tracking_id and stops.stop_num = location_view.stop_num + 1 and stops.location_id = "
                + location_ID.toString();

        QueryData results = new QueryData();

        try {
            ResultSet tracking = h2.query(trackingCall);
            TreeMap<Integer, String> stopList = new TreeMap<>();
            while(tracking.next()) {

                ResultSetMetaData rsmd = tracking.getMetaData();
                for (int i = 1; i < rsmd.getColumnCount() - 1; i++) {
                    System.out.println(i + " : " + rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
                }

                results.addData(new FutureTracking(tracking.getInt("tracking_ID"),
                        tracking.getInt("location_view.stop_num"),
                        tracking.getInt("location_view.location_ID"),
                        tracking.getString("location_name")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            results = h2.errorCall(results, trackingCall);
        }

        return results;
    }
}
