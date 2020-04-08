package de.dhbw.chaincar.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class VehicleLoader {

    private static VehicleLoader instance;
    private Context context;
    private static String TAG = "data.VehicleLoader";

    public static VehicleLoader getInstance(Context context) {
        if (instance == null) {
            instance = new VehicleLoader(context);
        }
        return instance;
    }

    private VehicleLoader(Context context) {
        this.context = context;
    }

    public List<Vehicle> getVehicles() throws Exception {
        List<Vehicle> vehicles = new ArrayList<>();
        Carchain contract = BCConnector.getInstance(context).getContract();
        contract.getAvailableVehicles().send().forEach(bigIntId -> {
            Log.d(TAG, "Loading Data for Car " + bigIntId);
            try {
                String id = bigIntId.toString();
                String name = contract.getNummernschild(id).send();
                String homeCountry = "DE";
                String homeCity = "";
                String homePostCode = "";
                String homeStreet = "";
                String homeStreetNumber = "";
                float pricePerHour = contract.getMietpreis(id).send().floatValue();
                float minRentDuration = contract.getMinMietdauer(id).send().floatValue();
                float maxRentDuration = contract.getMaxMietdauer(id).send().floatValue();

                Vehicle v = new Vehicle(id, name, homeCountry, homeCity, homePostCode,
                        homeStreet, homeStreetNumber, pricePerHour, minRentDuration, maxRentDuration);
                Log.d(TAG, "New Vehicle created, adding it now! Vehicle: " + v.toString());
                vehicles.add(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return vehicles;
    }


}
