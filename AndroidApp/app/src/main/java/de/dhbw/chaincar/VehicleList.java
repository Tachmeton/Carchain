package de.dhbw.chaincar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import de.dhbw.chaincar.adapter.AvailableVehicleAdapter;
import de.dhbw.chaincar.data.Vehicle;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleList extends Fragment {

    View view;
    private ListView vehicleListView;
    private ProgressBar vehicleListLoadingIndicator;

    public VehicleList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        vehicleListView = view.findViewById(R.id.availableVehicleList);
        vehicleListLoadingIndicator = view.findViewById(R.id.listLoadingIndicator);

        final ArrayList<Vehicle> vehicles = new ArrayList<>();
        //TODO: Get actual Vehicles from BC

        vehicles.add(new Vehicle("1", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 16.5f, 3, 8));
        vehicles.add(new Vehicle("2", "VW Golf 7 GTD", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 26f, 1, 24));
        vehicles.add(new Vehicle("3", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 12.5f, 1, 2));
        vehicles.add(new Vehicle("4", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 28.5f, 1, 48));
        vehicles.add(new Vehicle("5", "VW Golf 7 GTD", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 5, 10));
        vehicles.add(new Vehicle("6", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 30f, 1, 72));
        vehicles.add(new Vehicle("7", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 16.5f, 2, 6));
        vehicles.add(new Vehicle("8", "VW Golf 7 GTD", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 24f, 1, 12));

        AvailableVehicleAdapter vehicleAdapter = new AvailableVehicleAdapter(view.getContext(), vehicles);
        vehicleListView.setAdapter(vehicleAdapter);
        vehicleListLoadingIndicator.setVisibility(View.GONE);

        vehicleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VehicleInfoActivity.class);
                intent.putExtra("vehicle", vehicles.get(position));
                startActivity(intent);
            }
        });

        return view;
    }
}
