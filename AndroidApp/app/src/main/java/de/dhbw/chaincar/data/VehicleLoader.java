package de.dhbw.chaincar.data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

public class VehicleLoader {

    private static VehicleLoader instance;

    public static VehicleLoader getInstance(Context context) {
        if(instance == null){
            instance = new VehicleLoader();
        }
        return instance;
    }

    private VehicleLoader(){

    }

    public List<Vehicle> getVehicles(){

    }

    private Drawable[] getPicturesForVehicle(){

    }


}
