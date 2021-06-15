package android.lpv.main.retrofit;

import android.lpv.main.model.Car;
import android.lpv.main.model.LicensePlateRequest;
import android.lpv.main.model.ValidateCarDTO;
import android.lpv.main.utility.HttpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/cars/license_plate")
    Call<HttpResponse<ValidateCarDTO>> getCarByLicensePlate(@Body LicensePlateRequest request);

    @GET("/api/cars/in")
    Call<HttpResponse<List<Car>>> getAllInCars(@Query("search") String search);

    @POST("api/cars/status/{id}")
    Call<Void> changeCarStatus(@Path("id") long id);

}
