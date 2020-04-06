package de.dhbw.chaincar.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.content.ContextCompat;

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
    public float pricePerHour;
    public float minRentDuration;
    public float maxRentDuration;

    public Vehicle(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.homeCountry = in.readString();
        this.homeCity = in.readString();
        this.homePostCode = in.readString();
        this.homeStreet = in.readString();
        this.homeStreetNumber = in.readString();
        this.pricePerHour = in.readFloat();
        this.minRentDuration = in.readFloat();
        this.maxRentDuration = in.readFloat();
    }

    public Vehicle(String id, String name, String homeCountry, String homeCity, String homePostCode,
                   String homeStreet, String homeStreetNumber, float pricePerHour,
                   float minRentDuration, float maxRentDuration) {
        this.id = id;
        this.name = name;
        this.homeCountry = homeCountry;
        this.homeCity = homeCity;
        this.homePostCode = homePostCode;
        this.homeStreet = homeStreet;
        this.homeStreetNumber = homeStreetNumber;
        this.pricePerHour = pricePerHour;
        this.minRentDuration = minRentDuration;
        this.maxRentDuration = maxRentDuration;
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
        Random random = new Random();
        return (random.nextFloat() * 5f);
    }

    public Drawable[] getImages(Context context){
        return new Drawable[]{ ContextCompat.getDrawable(context, R.drawable.gti) };
    }
}
