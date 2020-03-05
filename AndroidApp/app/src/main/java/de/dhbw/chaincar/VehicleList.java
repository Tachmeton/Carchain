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

        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));
        vehicles.add(new Vehicle("123aksd5", "VW Golf 7 GTI", "Deutschland", "Reutlingen", "72764", "Musterstraße", "1", 22.5f, 1, 12));

        AvailableVehicleAdapter vehicleAdapter = new AvailableVehicleAdapter(view.getContext(), vehicles);
        vehicleListView.setAdapter(vehicleAdapter);
        vehicleListLoadingIndicator.setVisibility(View.GONE);

        vehicleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VehicleInfoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
