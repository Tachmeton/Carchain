package de.dhbw.chaincar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.chaincar.adapter.VehicleAdapter;
import de.dhbw.chaincar.data.Vehicle;

public class MainActivity extends AppCompatActivity {

    private ListView vehicleListView;
    private ProgressBar vehicleListLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleListView = findViewById(R.id.availableVehicleList);
        vehicleListLoadingIndicator = findViewById(R.id.listLoadingIndicator);

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));

        VehicleAdapter vehicleAdapter = new VehicleAdapter(this, vehicles);
        vehicleListView.setAdapter(vehicleAdapter);
        vehicleListLoadingIndicator.setVisibility(View.GONE);
    }


}
