package de.dhbw.chaincar.adapter;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import de.dhbw.chaincar.MyUtil;
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
        TextView distanceText = convertView.findViewById(R.id.vehicleDistance);
        TextView pricePerHour = convertView.findViewById(R.id.vehiclePrice);
        ImageView vehicleImage = convertView.findViewById(R.id.vehicleImage);
        ProgressBar loadingIndicator = convertView.findViewById(R.id.imageLoadingIndicator);

        nameText.setText(vehicle.name);
        minMaxRentDuration.setText(
                convertView.getResources().getString(R.string.vehicle_min_max_rent_duration)
                        .replace("{1}", String.valueOf(MyUtil.toTimeRange(vehicle.minRentDuration)))
                        .replace("{2}", String.valueOf(MyUtil.toTimeRange(vehicle.maxRentDuration))));

        float distance = vehicle.getDistance();
        distanceText.setText(convertView.getResources().getString(R.string.vehicle_distance)
                .replace("{1}", String.format("%.2f", distance)));

        float colorDistance = distance - .5f;
        if(colorDistance < 0f) colorDistance = 0f;
        if(colorDistance > 2f) colorDistance = 2f;
        colorDistance /= 2f;
        int color = (Integer) new ArgbEvaluator().evaluate(
                colorDistance,
                Color.parseColor("#2ecc71"),
                Color.parseColor("#e74c3c"));
        distanceText.setTextColor(color);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("EUR"));

        pricePerHour.setText(format.format(vehicle.pricePerHour));

        vehicleImage.setImageDrawable(vehicle.getImages(convertView.getContext())[0]);
        loadingIndicator.setVisibility(View.GONE);

        return convertView;
    }
}
