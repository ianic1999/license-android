package android.lpv.main.fragment;

import android.lpv.main.model.Car;
import android.lpv.main.retrofit.APIClient;
import android.lpv.main.retrofit.ApiService;
import android.lpv.main.utility.Adapter;
import android.lpv.main.utility.HttpResponse;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import isd.alpr_mobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private final ApiService apiService;
    private RecyclerView.Adapter<?> adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListFragment() {
        apiService = APIClient.getClient().create(ApiService.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new Adapter();
        layoutManager = new LinearLayoutManager(requireContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        ((TextInputEditText) view.findViewById(R.id.license_plate_filter)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getInCars(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getInCars(String search) {
        apiService.getAllInCars(search).enqueue(new Callback<HttpResponse<List<Car>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Car>>> call, @NonNull Response<HttpResponse<List<Car>>> response) {
                ((Adapter) adapter).submitList(Objects.requireNonNull(response.body()).data);
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Car>>> call, @NonNull Throwable t) {
                showToast();
            }
        });
    }

    private void showToast() {
        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
    }
}