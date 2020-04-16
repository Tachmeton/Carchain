package de.dhbw.chaincar.data;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import java8.util.concurrent.CompletableFuture;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class VehicleLoader {

    private static VehicleLoader instance;
    private Context context;
    private static String TAG = "data.VehicleLoader";

    private String carWallet = "0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509";


    private String baseURLImageServer = "193.196.54.51:3000";

    public static VehicleLoader getInstance(Context context) {
        if (instance == null) {
            instance = new VehicleLoader(context);
        }
        return instance;
    }

    private VehicleLoader(Context context) {
        this.context = context;
    }

    public ArrayList<Vehicle> getVehicles() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Carchain contract = BCConnector.getInstance(context).getContract();
        Log.d(TAG, "is valid contract: " + contract.isValid());
        Log.d(TAG, "" + contract.getFarbe(carWallet).send());
        //CompletableFuture<List> future = contract.getAvailableVehicles().sendAsync();
        /*contract.getAvailableVehicles().flowable().subscribe(x -> {
            Log.d(TAG, "Received flowable list, size: "+ x.size());
        });*/

//        contract.getAvailableVehicles().send().forEach(bigIntId -> {
//            Log.d(TAG, "Loading Data for Car " + bigIntId);
//            try {
//                String id = bigIntId.toString();
//                String name = contract.getNummernschild(id).send();
//                String homeCountry = "DE";
//                String homeCity = "";
//                String homePostCode = "";
//                String homeStreet = "";
//                String homeStreetNumber = "";
//                float pricePerHour = contract.getMietpreis(id).send().floatValue();
//                float minRentDuration = contract.getMinMietdauer(id).send().floatValue();
//                float maxRentDuration = contract.getMaxMietdauer(id).send().floatValue();
//
//                Vehicle v = new Vehicle(id, name, homeCountry, homeCity, homePostCode,
//                        homeStreet, homeStreetNumber, pricePerHour, minRentDuration, maxRentDuration);
//
//                Log.d(TAG, "Downloading pictures from Server now!");
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(baseURLImageServer + name)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .build();
//
//                // create an instance of the ApiService
//                ServerApi apiService = retrofit.create(ServerApi.class);
//                // make a request by calling the corresponding method
//                ImageResponse imageResponse = apiService.getVehiclePictures(name).blockingGet();
//                Log.d(TAG, "Received images! --> " + imageResponse.toCompactString());
//                v.addImages(imageResponse.getImagejson());
//
//
//                Log.d(TAG, "New Vehicle created, adding it now! Vehicle: " + v.toString());
//                vehicles.add(v);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        return vehicles;
    }


}
