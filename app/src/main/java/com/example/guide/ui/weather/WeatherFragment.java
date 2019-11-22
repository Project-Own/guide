package com.example.guide.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.guide.Model.Weather.Weather;
import com.example.guide.Model.Weather.WeatherData;
import com.example.guide.R;
import com.example.guide.databinding.WeatherFragmentBinding;

public class WeatherFragment extends Fragment {

    private WeatherViewModel mViewModel;
    private View contentView;
    private View loadingView;
    private int shortAnimationDuration;
    TextView errorTextView;
    private ImageView weatherImage;
    String iconName = "10d";
    SwipeRefreshLayout swipeRefreshLayout;
    WeatherFragmentBinding binding;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.bind(inflater.inflate(R.layout.weather_fragment, container, false));
        View v = binding.getRoot();
        weatherImage = v.findViewById(R.id.weatherImage);

        contentView = v.findViewById(R.id.mainContainer);
        loadingView = v.findViewById(R.id.loading_spinner);
        errorTextView = v.findViewById(R.id.errorText);

        swipeRefreshLayout = v.findViewById(R.id.weatherSwipeRefreshLayout);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        // TODO: Use the ViewModel

        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.loadData().observe(this, new Observer<WeatherData>() {
            @Override
            public void onChanged(WeatherData weatherData) {

                String weatherDescription = "";
                for (Weather weather : weatherData.getWeather()) {
                    weatherDescription = weather.getDescription() + "\n";
                    iconName = weather.getIcon();
                }
                String weatherIconUrl = "https://openweathermap.org/img/wn/" + iconName + "@2x.png";

                Glide.with(getActivity())
                        .load(weatherIconUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_close_black_24dp)
                        .into(weatherImage)
                ;


            }
        });

    }

}