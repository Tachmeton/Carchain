package de.dhbw.chaincar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import de.dhbw.chaincar.R;
import de.dhbw.chaincar.data.Vehicle;

public class AvailableVehicleAdapter extends ArrayAdapter<Vehicle> {

    public AvailableVehicleAdapter(Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Vehicle vehicle = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_list_item, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.vehicleName);
        TextView minMaxRentDuration = convertView.findViewById(R.id.minMaxRentDuration);
        TextView distance = convertView.findViewById(R.id.vehicleDistance);
        TextView pricePerHour = convertView.findViewById(R.id.vehiclePrice);

        nameText.setText(vehicle.name);
        minMaxRentDuration.setText(
                convertView.getResources().getString(R.string.vehicle_min_max_rent_duration)
                        .replace("{1}", String.valueOf(vehicle.minRentDuration))
                        .replace("{2}", String.valueOf(vehicle.maxRentDuration)));
        distance.setText(convertView.getResources().getString(R.string.vehicle_distance)
                .replace("{1}", String.valueOf(vehicle.getDistance())));

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("EUR"));

        pricePerHour.setText(format.format(vehicle.pricePerHour));

        return convertView;
    }
}
