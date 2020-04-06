package de.dhbw.chaincar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import de.dhbw.chaincar.data.Vehicle;

public class VehicleInfoActivity extends AppCompatActivity {

    private TextView price;
    private TextView rentDurationText;
    private float rentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        Intent intent = getIntent();
        final Vehicle vehicle = intent.getParcelableExtra("vehicle");

        rentDuration = vehicle.minRentDuration;

        ImageView btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView vehicleName = findViewById(R.id.vehicleName);
        TextView streetAndNumber = findViewById(R.id.streetAndNumber);
        TextView postCodeAndCity = findViewById(R.id.postCodeAndCity);
        ImageView vehicleImage = findViewById(R.id.galery);
        TextView country = findViewById(R.id.country);
        price = findViewById(R.id.vehiclePrice);
        rentDurationText = findViewById(R.id.rentDurationText);
        SeekBar rentDurationSeekBar = findViewById(R.id.rentDuration);
        Button buttonRent = findViewById(R.id.buttonRent);

        vehicleName.setText(vehicle.name);

        vehicleImage.setImageDrawable(vehicle.getImages(this)[0]);

        streetAndNumber.setText(getString(R.string.street_and_number)
                .replace("{1}", vehicle.homeStreet)
                .replace("{2}", vehicle.homeStreetNumber));

        postCodeAndCity.setText(getString(R.string.postcode_and_city)
                .replace("{1}", vehicle.homePostCode)
                .replace("{2}", vehicle.homeCity));

        country.setText(getString(R.string.country)
                .replace("{1}", vehicle.homeCountry));


        final NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("EUR"));

        rentDurationText.setText(MyUtil.toTimeString(this, vehicle.minRentDuration));
        price.setText(getString(R.string.rent_price).replace("{1}", format.format(vehicle.pricePerHour * vehicle.minRentDuration)));

        rentDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateRentDuration(vehicle.minRentDuration + ((vehicle.maxRentDuration - vehicle.minRentDuration) * (progress / 100f)));
                price.setText(getString(R.string.rent_price).replace("{1}", format.format(vehicle.pricePerHour * rentDuration)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        buttonRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Renting " + vehicle.name + " for " + MyUtil.toTimeString(getBaseContext(), rentDuration) +
                                    " at a cost of " + format.format(vehicle.pricePerHour * rentDuration));
            }
        });
    }

    private void updateRentDuration(float val){
        int whole = (int)val;
        float frac = val - whole;

        if(frac < 0.25f) frac = 0f;
        if(frac >= 0.25f && frac < 0.75f) frac = .5f;
        if(frac >= 0.75f) frac = 1f;

        val = whole + frac;

        rentDurationText.setText(MyUtil.toTimeString(this, val));
        rentDuration = val;
    }
}
