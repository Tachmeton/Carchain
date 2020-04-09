package de.dhbw.chaincar.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerApi {
    @GET("/getImages/{name}") //S-VS-1234
    Single<ImageResponse> getVehiclePictures(@Path("name") String numberplate);
}
