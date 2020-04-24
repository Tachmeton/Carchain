package de.dhbw.chaincar.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RentVehicle implements Parcelable {

    public float rentDuration;
    public Vehicle vehicle;

    public RentVehicle(Parcel in){
        this.rentDuration = in.readFloat();
        this.vehicle = in.readParcelable(Vehicle.class.getClassLoader());
    }

    public RentVehicle(float rentDuration, Vehicle vehicle){
        this.rentDuration = rentDuration;
        this.vehicle = vehicle;
    }

    public static final Creator<RentVehicle> CREATOR = new Creator<RentVehicle>() {
        @Override
        public RentVehicle createFromParcel(Parcel in) {
            return new RentVehicle(in);
        }

        @Override
        public RentVehicle[] newArray(int size) {
            return new RentVehicle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(rentDuration);
        dest.writeParcelable(vehicle, flags);
    }
}
