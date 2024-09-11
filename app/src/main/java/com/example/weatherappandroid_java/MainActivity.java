package com.example.weatherappandroid_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherappandroid_java.models.WeatherModel;
import com.example.weatherappandroid_java.utils.Constant;
import com.example.weatherappandroid_java.utils.NetworkResult;
import com.example.weatherappandroid_java.utils.WeatherGridAdapter;
import com.example.weatherappandroid_java.viewModels.WeatherViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    WeatherViewModel weatherViewModel;
    ArrayList<WeatherModel> weatherArrayList;
    ProgressBar progressBar;
    CardView cardView;
    TextView txtCity, txtCountry, txtTemperature, txtWeatherType;
    RecyclerView weatherRView;
    ImageView imgCloud, imgSearch;
    TextInputEditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayout();
    }

    private void initializeLayout() {

        etLocation = findViewById(R.id.etLocation);
        imgCloud = findViewById(R.id.imgCloud);
        imgSearch = findViewById(R.id.imgSearch);
        weatherRView = findViewById(R.id.weatherRView);
        txtWeatherType = findViewById(R.id.txtWeatherType);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtCountry = findViewById(R.id.txtCountry);
        txtCity = findViewById(R.id.txtCity);
        cardView = findViewById(R.id.cardView);
        progressBar = findViewById(R.id.progressBar);
        weatherArrayList = new ArrayList<>();
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        getWeatherObserver();
        setupSearchButton();

    }

    private void getWeatherObserver() {
        weatherViewModel.getWeatherLiveData().observe(this, weatherModel -> {
            progressBar.setVisibility(View.GONE);
            Timber.e("Response-observer: %s",weatherModel.getLocation().getCountry());
            if (weatherModel != null) {
                txtCity.setText("");
                txtCountry.setText("");
                txtTemperature.setText("");
                //txtHumidity.setText("Humidity: " + weatherModel.getMain().getHumidity() + "%");
            }
        });

        weatherViewModel.getErrorLiveData().observe(this, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupSearchButton() {
        Timber.e("setupSearchButton");
        imgSearch.setOnClickListener(v -> {
            Timber.e("Button-Click: %s", etLocation.getText().toString());
            String city = etLocation.getText().toString();
            if (!city.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                weatherViewModel.fetchWeather(city, Constant.apiKey, MainActivity.this); // Replace with your actual API key
            } else {
                Toast.makeText(MainActivity.this, "Enter Location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

    /*private void setLocationData(WeatherModel weatherData) {
        cardView.setVisibility(View.VISIBLE); // equivalent to isVisible = true
        txtCity.setText(weatherData.getLocation().getName());
        txtCountry.setText(weatherData.getLocation().getCountry());
        txtTemperature.setText(weatherData.getCurrent().getTemp_c() + "° c");

        // Load the image with URL adjustment
        Glide.with(this)
                .load("https:" + weatherData.getCurrent().getCondition().getIcon().replace("64x64", "128x128"))
                .into(imgCloud);
        imgCloud.setVisibility(View.VISIBLE); // equivalent to isVisible = true

        txtWeatherType.setText(weatherData.getCurrent().getCondition().getText());

        // Add weather data to the list and set up RecyclerView
        weatherArrayList.add(weatherData);
        weatherRView.setAdapter(new WeatherGridAdapter(weatherArrayList));
        weatherRView.setLayoutManager(new GridLayoutManager(this, 2));
    }*/
}