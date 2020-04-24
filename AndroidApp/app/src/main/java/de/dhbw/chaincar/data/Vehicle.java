package de.dhbw.chaincar.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.content.ContextCompat;
import java.util.List;
import java.util.Random;
import de.dhbw.chaincar.R;

public class Vehicle implements Parcelable {
    public String id;
    public String name;
    public String homeCountry;
    public String homeCity;
    public String homePostCode;
    public String homeStreet;
    public String homeStreetNumber;
    public String numberPlate;
    public String model;
    public String vehicleType;
    public String manufacturer;
    public String vehicleColor;
    public int ps;
    public float pricePerHour;
    public float minRentDuration;
    public float maxRentDuration;
    private List<Image> images;

    public Vehicle(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.homeCountry = in.readString();
        this.homeCity = in.readString();
        this.homePostCode = in.readString();
        this.homeStreet = in.readString();
        this.homeStreetNumber = in.readString();
        this.numberPlate = in.readString();
        this.model = in.readString();
        this.vehicleType = in.readString();
        this.manufacturer = in.readString();
        this.vehicleColor = in.readString();
        this.ps = in.readInt();
        this.pricePerHour = in.readFloat();
        this.minRentDuration = in.readFloat();
        this.maxRentDuration = in.readFloat();
    }

    public Vehicle(String id, String name, String homeCountry, String homeCity, String homePostCode,
                   String homeStreet, String homeStreetNumber, String numberPlate, String model, String vehicleType,
                   String manufacturer, String vehicleColor, int ps, float pricePerHour,
                   float minRentDuration, float maxRentDuration) {
        this.id = id;
        this.name = name;
        this.homeCountry = homeCountry;
        this.homeCity = homeCity;
        this.homePostCode = homePostCode;
        this.homeStreet = homeStreet;
        this.homeStreetNumber = homeStreetNumber;
        this.numberPlate = numberPlate;
        this.model = model;
        this.vehicleType = vehicleType;
        this.manufacturer = manufacturer;
        this.vehicleColor = vehicleColor;
        this.ps = ps;
        this.pricePerHour = pricePerHour;
        this.minRentDuration = minRentDuration;
        this.maxRentDuration = maxRentDuration;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImages(Image... imagesToAdd){
        for(Image img : imagesToAdd){
            this.images.add(img);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(homeCountry);
        dest.writeString(homeCity);
        dest.writeString(homePostCode);
        dest.writeString(homeStreet);
        dest.writeString(homeStreetNumber);
        dest.writeString(numberPlate);
        dest.writeString(model);
        dest.writeString(vehicleType);
        dest.writeString(manufacturer);
        dest.writeString(vehicleColor);
        dest.writeInt(ps);
        dest.writeFloat(pricePerHour);
        dest.writeFloat(minRentDuration);
        dest.writeFloat(maxRentDuration);
    }

    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public float getDistance(){
        return 1.25f;
    }

    public Drawable[] getImages(Context context){
        return new Drawable[]{ ContextCompat.getDrawable(context, R.drawable.gti) };
    }
}
