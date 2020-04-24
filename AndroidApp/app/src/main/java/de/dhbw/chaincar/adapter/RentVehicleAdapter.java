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
import de.dhbw.chaincar.data.RentVehicle;
import de.dhbw.chaincar.data.Vehicle;

public class RentVehicleAdapter extends ArrayAdapter<RentVehicle> {

    public RentVehicleAdapter(Context context, ArrayList<RentVehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RentVehicle rentVehicle = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rent_vehicle_list_item, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.vehicleName);
        TextView rentDuration = convertView.findViewById(R.id.rentDuration);
        TextView distanceText = convertView.findViewById(R.id.vehicleDistance);
        ImageView vehicleImage = convertView.findViewById(R.id.vehicleImage);
        ProgressBar loadingIndicator = convertView.findViewById(R.id.imageLoadingIndicator);

        nameText.setText(rentVehicle.vehicle.name);
        rentDuration.setText(
                convertView.getResources().getString(R.string.vehicle_rent_duration)
                        .replace("{1}", String.valueOf(MyUtil.toTimeRange(rentVehicle.rentDuration))));

        float distance = rentVehicle.vehicle.getDistance();
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


        vehicleImage.setImageDrawable(rentVehicle.vehicle.getImages(convertView.getContext())[0]);
        loadingIndicator.setVisibility(View.GONE);

        return convertView;
    }
}
