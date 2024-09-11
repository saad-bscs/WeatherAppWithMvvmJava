package com.example.weatherappandroid_java.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappandroid_java.R;
import com.example.weatherappandroid_java.models.WeatherModel;

import java.util.List;

public class WeatherGridAdapter extends RecyclerView.Adapter<WeatherGridAdapter.ViewHolder> {

    private final List<WeatherModel> weatherList;

    // Constructor
    public WeatherGridAdapter(List<WeatherModel> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.txtTitle.setText("Humidity");
                holder.txtDescription.setText(weatherList.get(0).getCurrent().getHumidity());
                break;
            case 1:
                holder.txtTitle.setText("Wind Speed");
                holder.txtDescription.setText("");//(weatherList.get(0).getCurrent().getWind_kph() + " km/h");
                break;
            case 2:
                holder.txtTitle.setText("UV");
                holder.txtDescription.setText("");//(weatherList.get(0).getCurrent().getUv());
                break;
            case 3:
                holder.txtTitle.setText("Participation");
                holder.txtDescription.setText("");//(weatherList.get(0).getCurrent().getPrecip_mm() + " mm");
                break;
            case 4:
                holder.txtTitle.setText("Local Time");
                holder.txtDescription.setText(weatherList.get(0).getLocation().getLocaltime().split(" ")[1]);
                break;
            case 5:
                holder.txtTitle.setText("Local Date");
                holder.txtDescription.setText(weatherList.get(0).getLocation().getLocaltime().split(" ")[0]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 6; // Static count as per the original code
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtValue);
        }
    }
}
